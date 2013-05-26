package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.site.tux.entity.TuxsvrsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * TuxSvrs Dao
 * User: qc
 * Date: 11-9-19
 * Time: 下午2:42
 */
public interface TuxSvrsDao extends PagingAndSortingRepository<TuxsvrsEntity, Integer> {
}
