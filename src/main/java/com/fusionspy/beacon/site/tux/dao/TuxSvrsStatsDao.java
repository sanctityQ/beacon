package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.site.tux.entity.TuxsvrStatsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * TuxSvrsStats Dao
 * User: qc
 * Date: 11-9-19
 * Time: 下午2:44
 */
public interface TuxSvrsStatsDao extends PagingAndSortingRepository<TuxsvrStatsEntity, Integer> {
}
