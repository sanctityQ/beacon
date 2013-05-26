package com.fusionspy.beacon.system.dao;

import com.fusionspy.beacon.system.entity.SiteListEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SiteListDao extends PagingAndSortingRepository<SiteListEntity,String> {
}
