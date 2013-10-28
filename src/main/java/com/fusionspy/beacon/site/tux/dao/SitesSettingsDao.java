package com.fusionspy.beacon.site.tux.dao;


import com.fusionspy.beacon.site.tux.entity.SiteSettingsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SitesSettingsDao extends PagingAndSortingRepository<SiteSettingsEntity, String> {


}
