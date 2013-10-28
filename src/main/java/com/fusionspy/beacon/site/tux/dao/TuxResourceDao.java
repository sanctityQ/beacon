package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.site.tux.entity.TuxresourceEntity;
import com.fusionspy.beacon.report.Statistics;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * Tux Resource Dao
 * User: qc
 * Date: 11-9-19
 * Time: 下午5:05
 */
@Repository
public interface TuxResourceDao extends PagingAndSortingRepository<TuxresourceEntity, Integer> {

    @SQL("select MAX(100-u.cpuidle) max,MIN(100-u.cpuidle) min,AVG(100-u.cpuidle) avg from GE_MONITOR_TUX_RESOURCE u where u.sitename = ?1 and u.rectime >= ?2 and  u.rectime < ?3")
    public Statistics statisticHostCpuUsedByRectimeBetween(String siteName, Timestamp startTime, Timestamp endTime);

    @SQL("select MAX(u.memFree) max,MIN(u.memFree) min,AVG(u.memFree) avg from GE_MONITOR_TUX_RESOURCE u where u.sitename = ?1 and u.rectime >= ?2 and  u.rectime < ?3")
    public Statistics statisticHostMemFreeByRectimeBetween(String siteName, Timestamp startTime, Timestamp endTime);

    @SQL("select MAX(u.allSvrCpuUse) max,MIN(u.allSvrCpuUse) min,AVG(u.allSvrCpuUse) avg from GE_MONITOR_TUX_RESOURCE u where u.sitename = ?1 and u.rectime >= ?2 and  u.rectime < ?2")
    public Statistics statisticServiceCpuUsedByRectimeBetween(String siteName, Timestamp startTime, Timestamp endTime);

    @SQL("select MAX(u.allSvrMemUsed) max,MIN(u.allSvrMemUsed) min,AVG(u.allSvrMemUsed) avg from GE_MONITOR_TUX_RESOURCE u where u.sitename = ?1 and u.rectime >= ?2 and  u.rectime < ?3")
    public Statistics statisticServiceMemUsedByRectimeBetween(String siteName,Timestamp startTime,Timestamp endTime);

}
