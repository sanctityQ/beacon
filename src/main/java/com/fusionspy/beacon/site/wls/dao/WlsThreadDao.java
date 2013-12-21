package com.fusionspy.beacon.site.wls.dao;
// Generated 2013-9-20 23:36:28 by One Data Tools 1.0.0

import com.fusionspy.beacon.report.Statistics;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fusionspy.beacon.site.wls.entity.WlsThread;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WlsThreadDao extends PagingAndSortingRepository<WlsThread, Integer> {

    @SQL("select server_name name, MAX(idle_count) max,MIN(idle_count) min,AVG(idle_count) avg from ge_monitor_wls_thread u " +
            "where u.site_name = ?1 and u.rec_time >= ?2 and u.rec_time < ?3 group by server_name")
    List<Statistics> statisticIdleCount(String resourceId, Timestamp startDate, Timestamp endDate);


    @SQL("select server_name name, MAX(thoughput) max,MIN(thoughput) min,AVG(thoughput) avg from ge_monitor_wls_thread u " +
            "where u.site_name = ?1 and u.rec_time >= ?2 and u.rec_time < ?3 group by server_name")
    List<Statistics> statisticThought(String resourceId, Timestamp startDate, Timestamp endDate);
}

