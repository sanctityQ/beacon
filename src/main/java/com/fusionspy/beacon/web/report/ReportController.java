package com.fusionspy.beacon.web.report;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fusionspy.beacon.report.*;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.resources.domain.ResourcesCache;
import com.sinosoft.one.monitor.utils.Reflections;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Path
public class ReportController {

    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

    private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private StatisticFactory factory;


    @Autowired
    private ResourcesCache resourcesCache;

    @Get
    public String view(Invocation inv){
        //TODO 先写死，后期需要调整为ajax动态加载属性值
        List<Attribute> attributes = factory.getAttributes(ResourceType.Tuxedo);
        inv.addModel("attributes",attributes);
        return "view";
    }


    @Get("/resourceType/{type}/attribute/{attribute}")
    public String serverResource(@Param("type")ResourceType type,@Param("attribute")String attribute,
                                 @Param("resourceId")String resourceId,@Param("dateSeries")DateSeries series,
                                 Invocation inv){

        StatisticReport statisticReport = factory.getInstance(type, attribute);



        ReportResult reportResult = statisticReport.getStatistic(resourceId,series);
        toGridJsonData(reportResult,series,inv);


        //view data
        inv.addModel("dateSeries",series);
        //TODO resourceType应该细化到tuxedo，现在仍然为APP_SERVER级别,当调整后可直接通过resource来获取到type
        inv.addModel("resourceType",type);
        inv.addModel("resource", StringUtils.isBlank(resourceId)?resourceId:resourcesCache.getResource(resourceId));
        inv.addModel("attribute",statisticReport.getAttribute());

        return "info";
    }

    @Get("/resourceType/{type}/attribute/{attribute}/top")
    public String top(@Param("type")ResourceType type,@Param("attribute")String attribute,
                      @Param("resourceId")String resourceId,@Param("dateSeries")DateSeries series,
                      @Param("top")TopFilter top,Invocation inv){
        StatisticTopReport statisticReport = (StatisticTopReport)factory.getInstance(type, attribute);
        //set top default value
        if(top ==null)top=TopFilter.five;
        logger.debug("/resourceType/{type}/attribute/{attribute}/top resourceId:{}",resourceId);
        List list = statisticReport.statisticByTop(resourceId,series,top);
        topDeal(list,statisticReport.reportAttribute(),inv);


        //view data
        inv.addModel("dateSeries",series);
        //TODO resourceType应该细化到tuxedo，现在仍然为APP_SERVER级别,当调整后可直接通过resource来获取到type
        inv.addModel("resourceType",type);
        inv.addModel("resource", StringUtils.isBlank(resourceId)?resourceId:resourcesCache.getResource(resourceId));
        inv.addModel("attribute",statisticReport.getAttribute());
        inv.addModel("top",top);

        return "topInfo";
    }

    void topDeal(List rows, final ReportAttribute reportAttribute, Invocation inv){

        //chart data
        final List<String> xAxis = Lists.newArrayList();
        final List<Double> chartData = Lists.newArrayList();
        List<Map<String,String>> grid = Lists.transform(rows,new Function<Object, Map<String,String>>() {
            @Nullable
            @Override
            public Map<String,String> apply(@Nullable Object input) {
                Map<String,String> m = Maps.newHashMap();
                for(String attribute:reportAttribute.getAttributes()){
                    m.put(attribute,String.valueOf(Reflections.invokeGetter(input, attribute)));
                };

                xAxis.add(m.get(reportAttribute.getCategories()));
                chartData.add(Double.parseDouble(m.get(reportAttribute.getChartData())));
                return m;
            }
        });

        String cellString = Joiner.on(",").join(reportAttribute.getAttributes());
        Page page = new PageImpl(grid);
        Gridable gridable = new Gridable(page);
        gridable.setIdField(reportAttribute.getCategories());
        gridable.setCellStringField(cellString);
        inv.addModel("gridData", UIUtil.with(gridable).as(UIType.Json).getConvertResult());
        inv.addModel("chartCategories", JSON.toJSONString(xAxis));
        inv.addModel("chartData", JSON.toJSONString(chartData) );
    }



    private void toGridJsonData(ReportResult reportResult,final DateSeries series, Invocation inv){
        if(reportResult.getStatistics().isEmpty())
            return;
        //chart data
        final List<String> xAxis = Lists.newArrayList();
        final List<Double> chartData = Lists.newArrayList();



        List<Map<String,String>> rows = Lists.transform(reportResult.getStatistics(),new Function<Statistics, Map<String,String>>() {
            @Nullable
            @Override
            public Map<String,String> apply(@Nullable Statistics input) {
                Map<String,String> m = Maps.newHashMap();
                m.put("date", new DateTime(input.getStartTime()).toLocalDate().toString("yyyy-MM-dd"));
                if(series.equals(DateSeries.today)||series.equals(DateSeries.yesterday))
                    m.put("time",new DateTime(input.getStartTime()).toLocalTime().toString("HH:mm:ss"));
                m.put("max",input.getMax()==null?"-": toBigDemical(input.getMax()).toString());
                m.put("min",input.getMin()==null?"-":toBigDemical(input.getMin()).toString());
                m.put("avg", input.getAvg() == null ? "-" : toBigDemical(input.getAvg()).toString());
                if(series.equals(DateSeries.lastWeek)||series.equals(DateSeries.lastMonth))
                    xAxis.add(new DateTime(input.getStartTime()).toLocalDate().toString("yyyy-MM-dd"));
                else
                    xAxis.add(new DateTime(input.getStartTime()).toLocalTime().toString("HH:mm:ss"));
                chartData.add(input.getAvg() == null? 0d:input.getAvg());
                return m;
            }
        });
        Page<Map<String,String>> page = new PageImpl<Map<String,String>>(rows);
        Gridable<Map<String,String>> gridable = new Gridable<Map<String,String>> (page);

        String cellString = new String("date,time,max,min,avg");
        if(series.equals(DateSeries.lastWeek)||series.equals(DateSeries.lastMonth))
            cellString = new String("date,max,min,avg");

        gridable.setIdField("date");
        gridable.setCellStringField(cellString);

        inv.addModel("gridData", UIUtil.with(gridable).as(UIType.Json).getConvertResult());
        inv.addModel("chartCategories", JSON.toJSONString(xAxis));
        inv.addModel("chartData", JSON.toJSONString(chartData) );
        inv.addModel("startTime",dateTimeFormatter.print(reportResult.getStartTime()));
        inv.addModel("endTime",dateTimeFormatter.print(reportResult.getEndTime()));
        inv.addModel("maxAvg",reportResult.getMaxAvg() == null?"-":toBigDemical(reportResult.getMaxAvg()).toString());
        inv.addModel("minAvg",reportResult.getMinAvg() == null?"-":toBigDemical(reportResult.getMinAvg()).toString());
        inv.addModel("avg",reportResult.getAvg() == null?"-":toBigDemical(reportResult.getAvg()).toString());
    }

    private BigDecimal toBigDemical(Double value){

        BigDecimal b = new BigDecimal(value);
        try{
            b = b.setScale(0,BigDecimal.ROUND_HALF_UP);
        }catch (ArithmeticException e){
          logger.debug("scale is wrong value is {}",value);
        }
        return b;
    }


    @Post("export")
    public String export(@Param("title")String title,@Param("attribute")String attribute,
                         @Param("gridData")String gridData,@Param("gridTitle")String gridTitle,
            @Param("svg")String svg,Invocation inv) throws TranscoderException, DocumentException, IOException {

        Rectangle rect = new Rectangle(PageSize.A4);
        Document document = new Document(rect.rotate());

       // HeaderFooter header = new HeaderFooter(headerPhrase, false);
        //document.setMargins(5, 5, 5, 5);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        document.open();
        BaseColor basicColor = new BaseColor(63,111,159);
        //add title start
        Font font = getChineseFont();
        font.setSize(10);
        document.add(new Phrase(title,font));
        document.add(Chunk.NEWLINE);
        //add title end

        //add chart start
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(550);
        table.setWidthPercentage(55.067f);
        table.setLockedWidth(true);
        font = getChineseFont();
        font.setColor(BaseColor.WHITE);
        font.setSize(8);
        Phrase phrase = new Phrase("监控图形",font);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(basicColor);
        cell.setFixedHeight(20f);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(createImage(svg));
        cell.setBorderWidth(0.8f);
        cell.setBorderColor(basicColor);

        table.addCell(cell);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setHeaderRows(1);
        document.add(table);
        document.add(Chunk.NEWLINE);
        //add chart end

        //add grid start

        JSONArray grid_Titles = JSON.parseObject(gridTitle,JSONArray.class);
        JSONObject grid_Datas = JSON.parseObject(gridData);
        PdfPTable grid_table = new PdfPTable(grid_Titles.size());
        grid_table.setTotalWidth(600);


        Font grid_title_font = getChineseFont();
        grid_title_font.setSize(8);
        grid_title_font.setColor(BaseColor.WHITE);

        Font grid_body_font = getChineseFont();
        grid_body_font.setSize(8);
        grid_body_font.setColor(BaseColor.BLACK);


        for(Object grid_Title :grid_Titles){
            Phrase p_t = new Phrase(String.valueOf(grid_Title),grid_title_font);
            PdfPCell c = new PdfPCell(p_t);
            c.setVerticalAlignment(Element.ALIGN_BOTTOM);
            c.setHorizontalAlignment(Element.ALIGN_LEFT);
            c.setBackgroundColor(basicColor);
            c.setBorderColor(BaseColor.GRAY);
            c.setBorderWidth(0.8f);
            c.setFixedHeight(20);
            grid_table.addCell(c);
        }
        JSONArray jsonArray = grid_Datas.getJSONArray("rows");
        int rowIndex = 0;

        BaseColor evenCellColor = new BaseColor(245,245,245);
        BaseColor cellBorderColor = new BaseColor(207,207,207);

        for(Object object:jsonArray) {
            JSONArray array = ((JSONObject) object).getJSONArray("cell");
            BaseColor cellColor;
            if (rowIndex % 2 == 0) {
                cellColor = evenCellColor;
            }else{
                cellColor = BaseColor.WHITE;
            }

            for (Object o : array) {
                Phrase p_t = new Phrase(String.valueOf(o), grid_body_font);
                PdfPCell c = new PdfPCell(p_t);
                c.setVerticalAlignment(Element.ALIGN_BOTTOM);
                c.setHorizontalAlignment(Element.ALIGN_LEFT);
                c.setBackgroundColor(cellColor);
                c.setBorderColor(cellBorderColor);
                c.setBorderWidth(0.8f);
                grid_table.addCell(c);
            }
            rowIndex++;
        }
        grid_table.setHeaderRows(1);
        grid_table.setHorizontalAlignment(Element.ALIGN_LEFT);

        document.add(grid_table);
        //add grid data end

        document.close();
        HttpServletResponse response = inv.getResponse();
        response.setHeader("Expires", "0");

        //String fileName = new String((gridTitle+"_"+ LocalDate.now().toString(DateTimeFormat.fullDate())).getBytes("GB2312"), "ISO8859-1");
        String fileName = attribute+"_"+LocalDate.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
        response.addHeader("Content-Disposition", "attachment; filename="+fileName+".pdf");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the contentlength
        response.setContentLength(baos.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();

        return null;
    }


    /**
     * 将SVG矢量转换为Image
     * @param svg
     * @return
     * @throws TranscoderException
     * @throws BadElementException
     * @throws IOException
     */
    Image createImage(String svg) throws TranscoderException, BadElementException, IOException {
        Transcoder transcoder = new JPEGTranscoder();
        TranscoderInput input = new TranscoderInput(new StringReader(svg));
        ByteOutputStream bos = new ByteOutputStream();
        TranscoderOutput output = new TranscoderOutput(bos);
        transcoder.transcode(input, output);
        Image image = Image.getInstance(bos.getBytes());
        image.setAlignment(Image.ALIGN_JUSTIFIED_ALL);
//        image.setBorder(Image.BOX);
//        image.setBorderWidth(10);
//        image.setBorderColor(BaseColor.BLUE);
        image.scaleToFit(400, 700);
        //scalePercent
        return image;
    }


    private  final Font getChineseFont() {
        Font FontChinese = null;
        try {
            BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            FontChinese = new Font(bfChinese, 12, Font.NORMAL);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return FontChinese;
    }

}
