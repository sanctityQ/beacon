package com.fusionspy.beacon.report;


import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;

public interface StatisticsRepository extends PagingAndSortingRepository<Statistics,Long> {

    Statistics findByResourceIdAndAttributeAndStartTimeAndEndTime(String resourceId,String attribute,Timestamp startTime, Timestamp endTime);
}
