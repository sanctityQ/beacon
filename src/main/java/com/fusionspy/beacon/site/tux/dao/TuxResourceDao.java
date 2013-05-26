package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.site.tux.entity.TuxresourceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Tux Resource Dao
 * User: qc
 * Date: 11-9-19
 * Time: 下午5:05
 */
public interface TuxResourceDao extends PagingAndSortingRepository<TuxresourceEntity, Integer> {
}
