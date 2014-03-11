package com.fusionspy.beacon.report;

import com.fusionspy.beacon.common.ResourceType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ge_monitor_report_statistics")
public class Statistics implements java.io.Serializable{

    private static final long serialVersionUID = Statistics.class.hashCode();


    //enum Unit{day,hour}

    private Long id;

    private String resourceId;

    private ResourceType resourceType;

    private String name;

    private String attribute;

    private Date startTime;

    private Date endTime;

    private Double max;

    private Double min;

    private Double avg;

    private String condition;


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


//    @Column(name="unit")
//    @Enumerated(value = EnumType.STRING)
//    public Unit getUnit() {
//        return unit;
//    }
//
//    public void setUnit(Unit unit) {
//        this.unit = unit;
//    }

    @Column(name = "max")
    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    @Column(name = "min")
    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    @Column(name = "avg")
    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }


    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Lob
    @Column(name = "`condition`")
    public String getCondition() {
        return condition;
    }
}
