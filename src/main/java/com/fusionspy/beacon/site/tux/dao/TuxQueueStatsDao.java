package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.site.tux.entity.TuxqueStatsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Tux Queue Stats Dao
 * User: qc
 * Date: 11-9-19
 * Time: 下午1:23
 */
public interface TuxQueueStatsDao extends PagingAndSortingRepository<TuxqueStatsEntity, Integer> {
}
