package com.fusionspy.beacon.site.wls.dao;
// Generated 2013-9-20 23:36:28 by One Data Tools 1.0.0

import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.entity.WlsThread;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WlsThreadDao extends PagingAndSortingRepository<WlsThread, Integer> {

    @SQL("select u.server_name name, MAX(u.idle_count) max,MIN(u.idle_count) min, ROUND(AVG(u.idle_count),2) avg from ge_monitor_wls_thread u where u.site_name = ?1 and u.rec_time between ?2 and ?3 group by u.server_name")
    public List<Statistics> statisticIdleCount(String resourceId, Timestamp start, Timestamp end);

    @SQL("select u.server_name name, MAX(u.thoughput) max,MIN(u.thoughput) min, ROUND(AVG(u.thoughput),2) avg from ge_monitor_wls_thread u where u.site_name = ?1 and u.rec_time between ?2 and ?3 group by u.server_name")
    public List<Statistics> statisticThought(String resourceId, Timestamp start, Timestamp end);
}

