package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.site.tux.entity.SiteListEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SiteListDao extends PagingAndSortingRepository<SiteListEntity,String> {
}
