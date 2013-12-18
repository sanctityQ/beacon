package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.dao.TuxSvrsDao;
import com.fusionspy.beacon.site.tux.entity.TuxsvrsEntity;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

@Service
class ProgressMemUsedReport extends StaticTopForwardReport implements TuxReport{

    private Attribute attribute;

    @Autowired
    private TuxSvrsDao svrsDao;

    private ReportAttribute reportAttribute =   new ReportAttribute(newArrayList("progname", "memoryuse", "processid"),"progname","memoryuse");

    private final Comparator<Map<String, String>> comparator = new ProgressMemUsedComparator();

    @Override
    protected List<Map<String, String>> statisticByTop(String resourceId, DateTime startTime, DateTime endTime, int top) {
        PageRequest pageRequest = new PageRequest(0,top, Sort.Direction.DESC,"memoryuse");
        Page page = svrsDao.findByRectimeBetweenAndSitename(startTime.toDate(),
                endTime.toDate(),resourceId,pageRequest);
        return super.convert(page.getContent());
    }

    @Override
    protected Comparator<Map<String, String>> getComparable() {
        return comparator;
    }

    static class  ProgressMemUsedComparator implements Comparator<Map<String, String>>{
        @Override
        public int compare(Map<String, String> o1, Map<String, String> o2) {
            return ComparisonChain.start().compare(Float.valueOf(o1.get("memoryuse")),Float.valueOf(o2.get("memoryuse")))
                    .result();
        }
    }

    @Override
    public ReportAttribute reportAttribute() {
        return reportAttribute;
    }


    @Override
    public Attribute getAttribute() {
        if(attribute == null){
            attribute = new Attribute();
            attribute.setAttribute("PROGRESS_MEM_USED");
            attribute.setAttributeCn("进程MEM占用大小");
            attribute.setResourceType(ResourceType.Tuxedo);
            attribute.setUnits("K");
        }
        return attribute;
    }


}
