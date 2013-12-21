package com.fusionspy.beacon.site.wls.dao;
// Generated 2013-9-20 23:36:28 by One Data Tools 1.0.0

import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.entity.WlsResource;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface WlsResourceDao extends PagingAndSortingRepository<WlsResource, Integer> {

    @SQL("select MAX(100-u.cpu_idle) max,MIN(100-u.cpu_idle) min,AVG(100-u.cpu_idle) avg from ge_monitor_wls_resource u where u.site_name = ?1 and u.rec_time >= ?2 and  u.rec_time < ?3")
    public Statistics statisticHostCpuUsedByRectimeBetween(String siteName, Timestamp startTime, Timestamp endTime);

    @SQL("select MAX(u.mem_free) max,MIN(u.mem_free) min,AVG(u.mem_free) avg from ge_monitor_wls_resource u where u.site_name = ?1 and u.rec_time >= ?2 and  u.rec_time < ?3")
    public Statistics statisticHostMemFreeByRectimeBetween(String resourceId, Timestamp startTime, Timestamp endTime);
}

