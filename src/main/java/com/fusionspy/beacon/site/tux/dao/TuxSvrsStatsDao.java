package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.tux.entity.TuxsvrStatsEntity;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.Date;

/**
 * TuxSvrsStats Dao
 * User: qc
 * Date: 11-9-19
 * Time: 下午2:44
 */
public interface TuxSvrsStatsDao extends PagingAndSortingRepository<TuxsvrStatsEntity, Integer> {

    @SQL("select MAX(u.tpsDone) max,MIN(u.tpsDone) min,AVG(u.tpsDone) avg from GE_MONITOR_TUX_SVR_STATS u where u.sitename = ?1 and u.rectime >= ?2 and  u.rectime < ?3")
    public Statistics statisticTpsByRectimeBetween(String siteName, Timestamp startTime, Timestamp endTime);

    @SQL("delete u from GE_MONITOR_TUX_SVR_STATS u where u.rectime < ?1 ")
    void deleteByRecTimeBefore(Timestamp date);
}
