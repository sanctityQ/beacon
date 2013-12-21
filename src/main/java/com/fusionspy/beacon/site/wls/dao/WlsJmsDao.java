package com.fusionspy.beacon.site.wls.dao;
// Generated 2013-9-20 23:36:28 by One Data Tools 1.0.0

import com.fusionspy.beacon.report.Statistics;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fusionspy.beacon.site.wls.entity.WlsJms;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WlsJmsDao extends PagingAndSortingRepository<WlsJms, Integer> {

    @SQL("select name, MAX(BytesCurrentCount) max,MIN(BytesCurrentCount) min,AVG(BytesCurrentCount) avg from ge_monitor_wls_jvm u " +
            "where u.site_name = ?1 and u.rec_time >= ?2 and u.rec_time < ?3 group by name")
    List<Statistics> statisticBytesCurrentCount(String resourceId, Timestamp startDate, Timestamp endDate);
}

