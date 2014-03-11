package com.fusionspy.beacon.site.wls.dao;
// Generated 2013-9-20 23:36:28 by One Data Tools 1.0.0

import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.entity.WlsJms;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Repository
public interface WlsJmsDao extends PagingAndSortingRepository<WlsJms, Integer> {

    @SQL("select u.server_name name, MAX(u.bytes_current_count) max,MIN(u.bytes_current_count) min,AVG(u.bytes_current_count) avg " +
            "from ge_monitor_wls_jms u where u.site_name = ?1 and" +
            "#if(?4!=''){ u.name=?4 and} u.rec_time between ?2 and ?3 group by u.server_name")
    public List<Statistics> statisticBytesCurrentCount(String resourceId, Timestamp start, Timestamp end,String jmsName);

    @SQL("select distinct name from ge_monitor_wls_jms")
    Set<String> distinctJmsName();
}

