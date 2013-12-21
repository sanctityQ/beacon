package com.fusionspy.beacon.site.wls.dao;
// Generated 2013-9-20 23:36:28 by One Data Tools 1.0.0

import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.entity.WlsJdbc;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WlsJdbcDao extends PagingAndSortingRepository<WlsJdbc, Integer> {

    @SQL("select u.server_name name, MAX(u.active_count) max,MIN(u.active_count) min,AVG(u.active_count) avg from ge_monitor_wls_jdbc u where u.site_name = ?1 and u.rec_time between ?2 and ?3 group by u.server_name")
    public List<Statistics> statisticActiveCount(String resourceId, String start, String end);
}

