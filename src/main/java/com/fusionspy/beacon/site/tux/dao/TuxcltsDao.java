package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.site.tux.entity.TuxcltsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * tuxcltsDao
 * User: qc
 * Date: 11-9-13
 * Time: 下午9:04
 */
public interface TuxcltsDao extends PagingAndSortingRepository<TuxcltsEntity, Integer> {
}
