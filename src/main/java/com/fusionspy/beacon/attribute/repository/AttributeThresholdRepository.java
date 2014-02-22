package com.fusionspy.beacon.attribute.repository;
// Generated 2013-3-1 10:54:17 by One Data Tools 1.0.0

import com.fusionspy.beacon.attribute.model.AttributeThreshold;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface AttributeThresholdRepository extends PagingAndSortingRepository<AttributeThreshold, String> {

    public AttributeThreshold findByResourceIdAndAttributeId(String resourceId,String attributeId);

    public AttributeThreshold findByResourceIdAndAttributeIdAndThresholdId(String resourceId,String attributeId,String thresholdId);
}

