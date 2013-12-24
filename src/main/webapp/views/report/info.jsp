<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>monitor监控系统</title>
<%@include file="/WEB-INF/layouts/base.jsp" %>
<script language="javascript" src="${ctx}/global/js/one.layout.js"></script>

    <script type="text/javascript">
    $(function(){
      //  $(".form").parent().parent().addClass("seleck").siblings().removeClass("seleck");
        $("body").layout();

        function query(){
            var loc = window.location.toString().split('&');
            var p = loc[0].split('?');
            loc[0] = p[0].substring(0,p[0].lastIndexOf("/")+1)+$('#attribute').val()+"?"+p[1];
            window.location=loc[0].substring(0,loc[0].indexOf("=")+1)+$('#dateSeries').val()+"&"+loc[1];
        }

        $('#attribute').html('');
        <c:forEach var="attribute" items="${attributes}" varStatus="status">
            $("#attribute").append("<option value='${attribute.attribute}' > ${attribute.attributeCn}</option> ");
        </c:forEach>
        $('#dateSeries').change(query);
        $('#attribute').change(query);

        <c:if test="${not empty dateSeries}">
        <%--时间间隔赋值--%>
        $('#dateSeries').val('${dateSeries}');
        </c:if>
        $('#attribute').val('${attribute.attribute}');
        <c:if test="${not empty gridData}">
        var grid_data = ${gridData};

        $("#sevenday_grid").Grid({
            girdData : grid_data,
            dataType: "json",
            height: 'auto',
            colums:[
                {id:'1',text:'日期/时间',name:"datetime",width:'',index:'1',align:'',color:''},
                {id:'2',text:'名称',name:"name",width:'',index:'1',align:'',color:''},
                {id:'3',text:'最大值',name:"max",width:'',index:'1',align:'',color:''},
                {id:'4',text:'最小值',name:"min",width:'',index:'1',align:'',color:''},
                {id:'5',text:'平均值',name:"avg",width:'',index:'1',align:'',color:''}
            ],
            //rowList:[10,20,30],
            pager : false,
            number:false,
            multiselect:false
        });

        var chart3 = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'column',
                margin: [ 50, 50, 50, 70]
            },
            title: {
                text: ''
            },
            xAxis: {
                categories: ${xAxisCategories},
                labels: {
                    rotation: -45,
                    align: 'right',
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: '${attribute.attributeCn}(${attribute.units})'
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -40,
                y: 50,
                floating: true,
                borderWidth: 1,
                backgroundColor: '#FFFFFF',
                shadow: true
            },
            credits: {
                text: '',
                href: ''
            },
            tooltip: {
                valueSuffix: '${attribute.units}',
                formatter: function() {
                    return '<b>${attribute.attributeCn}('+ this.x +
                            '):</b>'+ Highcharts.numberFormat(this.y, 1) +
                            ' ${attribute.units}';
                }
            },
            series: ${chartSeries}
        });

        $('#expPdf').click(function(e){
            var svg=chart3.getSVG();
            post_to_url('${ctx}/report/export',
                    {'svg':svg,
                        'title':'${dateSeries.description}-${type.description}的${attribute.attributeCn}(${attribute.units})',
                        'attribute':'${attribute.attribute}',
                        'gridData':'${gridData}',
                        'gridTitle':"['日期/时间'," +
                            <c:if test="${(dateSeries eq 'today') or (dateSeries eq 'yesterday')}">
                                "'时间'," +
                            </c:if>
                                "'最大值','最小值','平均值']"
                    });
        });
        </c:if>
    });

    //自动创建form并提交
    function post_to_url(path, params, method) {
        method = method || "post"; // Set method to post by default, if not specified.
        // The rest of this code assumes you are not using a library.
        // It can be made less wordy if you use one.
        var form = document.createElement("form");
        form.setAttribute("method", method);
        form.setAttribute("action", path);

        for(var key in params) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key].toString());

            form.appendChild(hiddenField);
        }
        document.body.appendChild(form);
        form.submit();
    }



</script>
</head>

<body>
<div id="layout_center">
    <div class="main" style="padding-bottom:60px">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="repor_tool">
            <tr>
                <td>
                    <select id="dateSeries" name="dateSeries" class="diySelect" style="width:80px">
                        <option value="today">今天</option>
                        <option value="yesterday">昨天</option>
                        <option value="lastWeek">最近一周</option>
                        <option value="lastMonth">最近一月</option>
                    </select>
                </td>
                <td align="right">选择属性：</td>
                <td width="120">
                    <select id='attribute' name="attribute" class="diySelect" style="width:120px">
                        <option value="CPU_IDLE">主机CPU使用率</option>
                        <option value="MEM_FREE">主机内存空闲</option>
                        <option value="SVR_CPU_USED">服务资源CPU使用率</option>
                        <option value="SVR_MEM_USED">服务资源内存占用</option>
                        <option value="CLT_COUNT">客户端数量</option>
                        <option value="CLT_BUSY_COUNT">繁忙客户端数量</option>
                        <option value="QUE_BLOCK_COUNT">队列阻塞消息数量</option>
                        <option value="TPS_DONE">TPS</option>
                    </select>
                </td>
                <td width="100" align="right"><a id='expPdf' href="javascript:void(0);" class="expor_pdf"><img src="${ctx}/global/images/icon_pdf.gif" width="16" height="16" />导出PDF</a></td>
            </tr>
        </table>
        <c:if test="${empty gridData}">
            <p class="hint">暂未数据……</p>
            <br />
        </c:if>

        <c:if test="${not empty gridData}">
        <div class="threshold_file">
            <div class="sub_title">${dateSeries.description}-${type.description}的${attribute.attributeCn}(${attribute.units})</div>
            <table class="base_info" width="100%" cellpadding="0" cellspacing="0">
                <tr>
                    <td>监视器名称</td>
                    <td>${resource.resourceName}</td>
                </tr>
                <tr>
                    <td>属性 </td>
                    <td>${attribute.attributeCn} </td>
                </tr>
                <tr>
                    <td>从 </td>
                    <td>${startTime}</td>
                </tr>
                <tr>
                    <td>到 </td>
                    <td>${endTime}</td>
                </tr>
                <tr>
                    <td colspan="2"><div id="container" style="width:98%" ></div>
                        <p class="explain">${type.description}的${attribute.attributeCn}:
                            <span> &nbsp; &nbsp;最小平均值:${minAvg}${attribute.units}</span>
                            <span>最大平均值:${maxAvg}${attribute.units}</span>
                            <span>平均: ${avg}${attribute.units}</span></p></td>
                </tr>
            </table>
        </div>
        <br />
        <div class="threshold_file">
            <div class="sub_title">${attribute.attributeCn}(${attribute.units})</div>
            <div id="sevenday_grid"></div>
        </div>
        <br />
        </c:if>
        <div class="threshold_file">
            <div class="sub_title">备注</div>
            <ul class="remark">
                <li><b>存档:</b> 所有数据每小时存档一次。例如早上10点到11点的数据被存档并标记为早上11点。如果在归档间隔的1小时中监视器实例完全停止，则本时段的该监视器的数据不进行归档.</li>
                <li><b>最小值:</b> 它表示一小时之内所收集的最低值。例如，2013年9月1日14点显示的6就表示从2013年9月1日13点到14点之间收集的最低值为'6'.</li>
                <li><b>最大值:</b> 它表示一小时之内所收集的最高值。例如，2013年9月1日14点显示的12就表示从2013年9月1日13点到14点之间收集的最高值为'12'.</li>
                <li><b>每小时平均值:</b> 它表示一小时之内对所收集值取的平均值。例如，2013年9月1日14点显示的9就表示从2013年9月1日13点到14点之间收集值的平均值为'9'.</li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
