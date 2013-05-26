package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.site.tux.entity.TuxquesEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

/**
 * tux ques operate
 * User: qc
 * Date: 11-9-19
 * Time: 上午11:22
 */
public interface TuxQueueDao extends PagingAndSortingRepository<TuxquesEntity, Integer> {
}
