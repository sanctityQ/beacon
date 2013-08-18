package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.tux.entity.TuxcltsStatsEntity;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * TuxcltsStats dao
 * User: qc
 * Date: 11-9-16
 * Time: 上午9:32
 */
@Repository
public interface TuxcltsStatsDao extends PagingAndSortingRepository<TuxcltsStatsEntity, Integer> {

    @SQL("select MAX(u.totalCount) max,MIN(u.totalCount) min,AVG(u.totalCount) avg from GE_MONITOR_TUX_CLT_STATS u where u.sitename = ?1 and u.rectime >= ?2 and  u.rectime < ?3")
    public Statistics statisticTotalCountByRectimeBetween(String siteName, Timestamp startTime, Timestamp endTime);

    @SQL("select MAX(u.busyCount) max,MIN(u.busyCount) min,AVG(u.busyCount) avg from GE_MONITOR_TUX_CLT_STATS u where u.sitename = ?1 and u.rectime >= ?2 and  u.rectime < ?3")
    public Statistics statisticBusyCountByRectimeBetween(String siteName, Timestamp startTime, Timestamp endTime);
}
