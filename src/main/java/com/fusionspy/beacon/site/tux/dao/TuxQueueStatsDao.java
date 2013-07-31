package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.tux.entity.TuxqueStatsEntity;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;

/**
 * Tux Queue Stats Dao
 * User: qc
 * Date: 11-9-19
 * Time: 下午1:23
 */
public interface TuxQueueStatsDao extends PagingAndSortingRepository<TuxqueStatsEntity, Integer> {

    @SQL("select MAX(u.queueNum) max,MIN(u.queueNum) min,AVG(u.queueNum) avg from GE_MONITOR_TUX_QUE_STATS u where u.sitename = ?1 and u.rectime >= ?2 and  u.rectime < ?3")
    public Statistics statisticQueueBlockCountByRectimeBetween(String siteName, Timestamp startTime, Timestamp endTime);
}
