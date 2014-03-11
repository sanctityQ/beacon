package com.fusionspy.beacon.site.wls.dao;
// Generated 2013-9-20 23:36:28 by One Data Tools 1.0.0

import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.entity.WlsEjbpool;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Repository
public interface WlsEjbPoolDao extends PagingAndSortingRepository<WlsEjbpool, Integer> {

    @SQL("select u.server_name name, MAX(u.beans_inUser_current_count) max,MIN(u.beans_inUser_current_count) min,AVG(u.beans_inUser_current_count) avg " +
            "from ge_monitor_wls_ejbpool u where u.site_name = ?1 and" +
            "#if(?4!=''){ u.name=?4 and} u.rec_time between ?2 and ?3 group by u.server_name")
    public List<Statistics> statisticBeansInUseCurrentCount(String resourceId, Timestamp start, Timestamp end,String siteName);



    @SQL("select DISTINCT name from ge_monitor_wls_ejbpool")
    public Set<String> findDistinctEjbName();

}

