package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.dao.TuxSvrsDao;
import com.google.common.collect.ComparisonChain;
import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.common.ResourceType;
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
class ProgressCpuUsedReport extends StaticTopForwardReport implements TuxReport{

    private Attribute attribute;

    @Autowired
    private TuxSvrsDao svrsDao;

    private final Comparator<Map<String, String>> comparator = new ProgressCpuUsedComparator();

    private ReportAttribute reportAttribute = new ReportAttribute(newArrayList("progname", "cpuuse", "processid"),"progname","cpuuse");


    @Override
    protected List<Map<String,String>>  statisticByTop(String resourceId, DateTime startTime, DateTime endTime, int top) {
        PageRequest pageRequest = new PageRequest(0,top, Sort.Direction.DESC,"cpuuse");
        Page page =svrsDao.findByRectimeBetweenAndSitename(startTime.toDate(),endTime.toDate(),resourceId,pageRequest);
        return super.convert(page.getContent()) ;
    }

    @Override
    protected Comparator<Map<String, String>> getComparable() {
        return comparator;
    }

    @Override
    public ReportAttribute reportAttribute() {
        return reportAttribute;
    }

    @Override
    public Attribute getAttribute() {
        if(attribute == null){
            attribute = new Attribute();
            attribute.setAttribute("PROGRESS_CPU_USED");
            attribute.setAttributeCn("进程CPU使用率");
            attribute.setResourceType(ResourceType.Tuxedo);
            attribute.setUnits("%");
        }
        return attribute;
    }

    static class  ProgressCpuUsedComparator implements Comparator<Map<String, String>>{

        @Override
        public int compare(Map<String, String> o1, Map<String, String> o2) {
            return ComparisonChain.start().compare(Float.valueOf(o1.get("cpuuse")),Float.valueOf(o2.get("cpuuse")))
                    .result();

        }
    }
}
