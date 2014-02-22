package com.fusionspy.beacon.report;


import com.fusionspy.beacon.common.ResourceType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ge_monitor_report_statistics_top")
public class StatisticsTop implements java.io.Serializable{

    private Long id;

    private String resourceId;

    private ResourceType resourceType;

    private String attribute;

    private Date startTime;

    private Date endTime;

    private String topValue;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "resource_id")
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }


    @Column(name="resource_type")
    @Enumerated(value = EnumType.STRING)
    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }


    @Column(name = "attribute_name")
    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Column(name="start_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name="end_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name="top_value")
    public String getTopValue() {
        return topValue;
    }

    public void setTopValue(String topValue) {
        this.topValue = topValue;
    }
}
