package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.site.tux.entity.TuxcltsStatsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * TuxcltsStats dao
 * User: qc
 * Date: 11-9-16
 * Time: 上午9:32
 */
public interface TuxcltsStatsDao extends PagingAndSortingRepository<TuxcltsStatsEntity, Integer> {
}
