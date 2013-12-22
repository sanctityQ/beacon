package com.fusionspy.beacon.site.wls.dao;
// Generated 2013-9-20 23:36:28 by One Data Tools 1.0.0

import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.entity.WlsJvm;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WlsJvmDao extends PagingAndSortingRepository<WlsJvm, Integer> {

    @SQL("select u.server_name name, MAX(u.free_heap/1048576) max, MIN(u.free_heap/1048576) min, AVG(u.free_heap/1048576) avg from ge_monitor_wls_jvm u where u.site_name = ?1 and u.rec_time between ?2 and ?3 group by u.server_name")
    public List<Statistics> statisticFreeHeap(String resourceId, Timestamp start, Timestamp end);
}

