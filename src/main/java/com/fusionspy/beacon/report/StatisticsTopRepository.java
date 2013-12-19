package com.fusionspy.beacon.report;


import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface StatisticsTopRepository extends PagingAndSortingRepository<StatisticsTop,Long> {

    StatisticsTop findByResourceIdAndAttributeAndStartTimeAndEndTime(String resourceId,String attribute,
                                                                  Date startTime, Date endTime);
}
