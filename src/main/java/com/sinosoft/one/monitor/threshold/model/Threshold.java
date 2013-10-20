package com.sinosoft.one.monitor.threshold.model;
// Generated 2013-3-1 10:29:53 by One Data Tools 1.0.0


import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.persistence.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * Threshold.
* 阈值信息表
 */
@Entity
@Table(name="GE_MONITOR_THRESHOLD")
public class Threshold  implements java.io.Serializable {

    /**
    * 主键ID.
    */
    private String id;

    /**
     * 阈值类型
     */
    private ThresholdConstant.Type type;
    /**
    * 名称.
    */
    private String name;
    /**
    * 描述.
    */
    private String description;
    /**
    * 临界阈值条件.
    */
    private String criticalThresholdCondition;
    /**
    * 临界阈值值.
    */
    private String criticalThresholdValue;
    /**
    * 临界阈值信息.
    */
    private String criticalThresholdMessage;
    /**
    * 警告阈值条件.
    */
    private String warningThresholdCondition;
    /**
    * 警告阈值值.
    */
    private String warningThresholdValue;
    /**
    * 警告阈值信息.
    */
    private String warningThresholdMessage;
    /**
    * 正常阈值条件.
    */
    private String infoThresholdCondition;
    /**
    * 正常阈值值.
    */
    private String infoThresholdValue;
    /**
    * 正常阈值信息.
    */
    private String infoThresholdMessage;
	/**
	 * 结果信息
	 */
	private String resultMessage;
	/**
	 * 条件符号
	 */
	private String conditionStr;


	/**
	 * 警告阈值条件符号.
	 */
	private String warningThresholdConditionStr;

	/**
	 * 严重阈值条件符号.
	 */
	private String criticalThresholdConditionStr;

	/**
	 * 正常阈值条件符号.
	 */
	private String infoThresholdConditionStr;

	@Transient
	public String getWarningThresholdConditionStr() {
		return warningThresholdConditionStr;
	}
	@Transient
	public String getCriticalThresholdConditionStr() {
		return criticalThresholdConditionStr;
	}
	@Transient
	public String getInfoThresholdConditionStr() {
		return infoThresholdConditionStr;
	}

	private String operation="<a  href='javascript:void(0)' onclick='updRow(this)' class='eid'>编辑</a> <a href='javascript:void(0)' class='del' onclick='delRow(this)'>删除</a>";
	public Threshold() {
	}

	@Transient
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
    public Threshold(String id) {
        this.id = id;
    }
   
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="id", unique=true, length=32)
    public String getId() {
    return this.id;
    }

    public void setId(String id) {
    this.id = id;
    }

    @Column(name="type", length=10)
    @Enumerated(EnumType.STRING)
    public ThresholdConstant.Type getType() {
        return type;
    }

    public void setType(ThresholdConstant.Type type) {
        this.type = type;


//        if(this.getType().equals(ThresholdConstant.Type.NUMERIC)){
//	        this.criticalThresholdConditionStr = ThresholdConditions.valueOf(criticalThresholdCondition).symbol();
//        }else{
//            this.criticalThresholdConditionStr = criticalThresholdCondition;
//        }

    }


    
    @Column(name="name", length=100)
    public String getName() {
    return this.name;
    }

    public void setName(String name) {
    this.name = name;
    }
    
    @Column(name="description", length=250)
    public String getDescription() {
    return this.description;
    }

    public void setDescription(String description) {
    this.description = description;
    }
    
    @Column(name="critical_threshold_condition", length=2)
    public String getCriticalThresholdCondition() {
    return this.criticalThresholdCondition;
    }

    public void setCriticalThresholdCondition(String criticalThresholdCondition) {
        this.criticalThresholdCondition = criticalThresholdCondition;

    }
    
    @Column(name="critical_threshold_value", length = 250)
    public String getCriticalThresholdValue() {
    return this.criticalThresholdValue;
    }

    public void setCriticalThresholdValue(String criticalThresholdValue) {
    this.criticalThresholdValue = criticalThresholdValue;
    }
    
    @Column(name="critical_threshold_message", length=250)
    public String getCriticalThresholdMessage() {
    return this.criticalThresholdMessage;
    }

    public void setCriticalThresholdMessage(String criticalThresholdMessage) {
    this.criticalThresholdMessage = criticalThresholdMessage;
    }
    
    @Column(name="warning_threshold_condition", length=2)
    public String getWarningThresholdCondition() {
    return this.warningThresholdCondition;
    }

    public void setWarningThresholdCondition(String warningThresholdCondition) {
        this.warningThresholdCondition = warningThresholdCondition;
	   // this.warningThresholdConditionStr = ThresholdConditions.valueOf(warningThresholdCondition).symbol();
    }
    
    @Column(name="warning_threshold_value", precision=22, scale=0)
    public String getWarningThresholdValue() {
    return this.warningThresholdValue;
    }

    public void setWarningThresholdValue(String warningThresholdValue) {
    this.warningThresholdValue = warningThresholdValue;
    }
    
    @Column(name="warning_threshold_message", length=250)
    public String getWarningThresholdMessage() {
    return this.warningThresholdMessage;
    }

    public void setWarningThresholdMessage(String warningThresholdMessage) {
    this.warningThresholdMessage = warningThresholdMessage;
    }
    
    @Column(name="info_threshold_condition", length=2)
    public String getInfoThresholdCondition() {
    return this.infoThresholdCondition;
    }

    public void setInfoThresholdCondition(String infoThresholdCondition) {
        this.infoThresholdCondition = infoThresholdCondition;
	  //  this.infoThresholdConditionStr = ThresholdConditions.valueOf(infoThresholdCondition).symbol();
    }
    
    @Column(name="info_threshold_value", precision=22, scale=0)
    public String getInfoThresholdValue() {
        return this.infoThresholdValue;
    }

    public void setInfoThresholdValue(String infoThresholdValue) {
        this.infoThresholdValue = infoThresholdValue;
    }
    
    @Column(name="info_threshold_message", length=250)
    public String getInfoThresholdMessage() {
    return this.infoThresholdMessage;
    }

    public void setInfoThresholdMessage(String infoThresholdMessage) {
    this.infoThresholdMessage = infoThresholdMessage;
    }

	@Transient
	public String getResultMessage() {
		return resultMessage;
	}

	@Transient
	public String getConditionStr() {
		return conditionStr;
	}



    public SeverityLevel match(String currentValue) {
        if (this.getType().equals(ThresholdConstant.Type.PATTERN)) {
            if (StringUtils.isNotBlank(this.getCriticalThresholdValue())) {
                if (ThresholdConstant.Pattern.instance(this.getCriticalThresholdCondition())
                        .match(currentValue, this.getCriticalThresholdValue())) {
                    return SeverityLevel.CRITICAL;
                }
            }
            if (StringUtils.isNotBlank(this.getWarningThresholdValue())) {

                if (ThresholdConstant.Pattern.instance(this.getWarningThresholdCondition())
                        .match(currentValue, this.getWarningThresholdValue())) {
                    return SeverityLevel.WARNING;
                }
            }
            if (StringUtils.isNotBlank(this.getInfoThresholdValue())) {

                if (ThresholdConstant.Pattern.instance(this.getInfoThresholdCondition())
                        .match(currentValue, this.getInfoThresholdValue())) {
                    return SeverityLevel.INFO;
                }
            }
        }
        if (this.getType().equals(ThresholdConstant.Type.NUMERIC)) {
            if (StringUtils.isNotBlank(this.getCriticalThresholdValue())) {
                if (ThresholdConditions.instance(this.getCriticalThresholdCondition())
                        .match(new BigDecimal(currentValue), new BigDecimal(this.getCriticalThresholdValue())))
                    return SeverityLevel.CRITICAL;
            }

            if (StringUtils.isNotBlank(this.getWarningThresholdValue())) {
                if (ThresholdConditions.instance(this.getWarningThresholdCondition())
                        .match(new BigDecimal(currentValue), new BigDecimal(this.getWarningThresholdValue()))) {
                    return SeverityLevel.WARNING;
                }
            }
            if (StringUtils.isNotBlank(this.getInfoThresholdValue())) {
                if (ThresholdConditions.instance(this.getInfoThresholdCondition())
                        .match(new BigDecimal(currentValue), new BigDecimal(this.getInfoThresholdValue()))) {
                    return SeverityLevel.INFO;
                }
            }
        }
        return SeverityLevel.UNKNOWN;
    }


	/**
	 * 计算严重级别
	 * @param attributeValue 属性值
	 * @return 严重级别
	 */
	public SeverityLevel evalSeverityLevel(String attributeValue) {

		String condition = this.getCriticalThresholdCondition();
		String conditionValue = this.getCriticalThresholdValue();

        if (isThisLevel(condition, conditionValue, attributeValue)) {
            //此处拼接返回消息
            resultMessage = attributeValue + " " + conditionStr + " " + conditionValue;
            return SeverityLevel.CRITICAL;
        }



		condition = this.getWarningThresholdCondition();
		conditionValue = this.getWarningThresholdValue();
        if(this.getType().equals(ThresholdConstant.Type.NUMERIC)){
            if(isThisLevel(condition, conditionValue, attributeValue)) {
                resultMessage = attributeValue + " " + conditionStr + " " + conditionValue;
                resultMessage += " #U#, " + attributeValue + " " + ThresholdConditions.valueOf(criticalThresholdCondition).revertSymbol() + " " + criticalThresholdValue;
                return SeverityLevel.WARNING;
            }
        }

		condition = this.getInfoThresholdCondition();
		conditionValue = this.getInfoThresholdValue();
        if(this.getType().equals(ThresholdConstant.Type.NUMERIC)){
            if(isThisLevel(condition, conditionValue, attributeValue)) {
                resultMessage = attributeValue + " " + conditionStr + " " + conditionValue;
                return SeverityLevel.INFO;
            }

        }
		return SeverityLevel.UNKNOWN;
	}

    /**
     *
     * @param condition
     * @param conditionValue  阈值
     * @param attributeValue  当前值
     * @return
     */
	private boolean isThisLevel(String condition, String conditionValue, String attributeValue) {

        //数字类型
        if(this.getType().equals(ThresholdConstant.Type.NUMERIC)){
            if(ThresholdConditions.EQ.name().equals(condition)) {
                conditionStr = ThresholdConditions.EQ.symbol();
                //校验信息
                return new BigDecimal(conditionValue).equals(new BigDecimal(attributeValue));
            }
            if(ThresholdConditions.NE.name().equals(condition)) {
                conditionStr = ThresholdConditions.NE.symbol();
                return !new BigDecimal(conditionValue).equals(new BigDecimal(attributeValue));
            }
            if(ThresholdConditions.LT.name().equals(condition)) {
                conditionStr = ThresholdConditions.LT.symbol();
                return new BigDecimal(attributeValue).compareTo(new BigDecimal(conditionValue)) < 0;
            }
            if(ThresholdConditions.LE.name().equals(condition)) {
                conditionStr = ThresholdConditions.LE.symbol();
                return new BigDecimal(attributeValue).compareTo(new BigDecimal(conditionValue)) <= 0;
            }
            if(ThresholdConditions.GT.name().equals(condition)) {
                conditionStr = ThresholdConditions.GT.symbol();
                return new BigDecimal(attributeValue).compareTo(new BigDecimal(conditionValue)) > 0;
            }
            if(ThresholdConditions.GE.name().equals(condition)) {
                conditionStr = ThresholdConditions.GE.symbol();
                return new BigDecimal(attributeValue).compareTo(new BigDecimal(conditionValue)) >= 0;
            }
        }
        else if(this.getType().equals(ThresholdConstant.Type.PATTERN)){
             if(ThresholdConstant.Pattern.CONTAINS.getValue().equals(condition)){
                 conditionStr = ThresholdConstant.Pattern.CONTAINS.symbol();
                 //return
             }
        }




		throw new RuntimeException("invalid condition : " + condition);
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

    public static void main(String args[]){
        System.out.print(ThresholdConstant.Pattern.CONTAINS.symbol());
    }
}


