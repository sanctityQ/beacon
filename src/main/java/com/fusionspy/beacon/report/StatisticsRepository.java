package com.fusionspy.beacon.report;


import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.List;

public interface StatisticsRepository extends PagingAndSortingRepository<Statistics,Long> {

    List<Statistics> findByResourceIdAndAttributeAndStartTimeAndEndTime(String resourceId,String attribute,
                                                                  Timestamp startTime, Timestamp endTime);
}
