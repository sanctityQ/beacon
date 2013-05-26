package com.fusionspy.beacon.system.dao;


import com.fusionspy.beacon.system.entity.SiteSettingsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SitesSettingsDao extends PagingAndSortingRepository<SiteSettingsEntity, String> {


}
