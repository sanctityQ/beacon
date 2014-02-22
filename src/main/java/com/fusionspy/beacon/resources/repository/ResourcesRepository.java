package com.fusionspy.beacon.resources.repository;
// Generated 2013-3-1 10:54:17 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.fusionspy.beacon.common.ResourceType;
import com.fusionspy.beacon.resources.model.Resource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourcesRepository extends PagingAndSortingRepository<Resource, String> {

    @SQL("delete from GE_MONITOR_RESOURCES where RESOURCE_ID in (?1)")
    void deleteByMoitorIds(List<String> monitorId);

    Resource findByResourceId(String resourceId);

    List<Resource> findByResourceType(ResourceType resourceType);

    @SQL("select * from GE_MONITOR_RESOURCES a where a.RESOURCE_ID in (?1)")
    List<Resource> findAllResourcesWithUrlIds(String[] urlIds);
}

