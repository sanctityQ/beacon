package com.fusionspy.beacon.site.wls.dao;
// Generated 2013-9-20 23:36:28 by One Data Tools 1.0.0

import com.fusionspy.beacon.report.Statistics;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fusionspy.beacon.site.wls.entity.WlsWebapp;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WlsWebappDao extends PagingAndSortingRepository<WlsWebapp, Integer> {

    @SQL("select name, MAX(OpenSessionsCurrentCount) max,MIN(OpenSessionsCurrentCount) min,AVG(OpenSessionsCurrentCount) avg "+
            "from ge_monitor_wls_webapp u where u.site_name = ?1 and u.rec_time >= ?2 and  u.rec_time < ?3 group by name")
    List<Statistics> statisticOpenSessionCurrentCount(String resourceId, Timestamp startDate, Timestamp endDate);
}

