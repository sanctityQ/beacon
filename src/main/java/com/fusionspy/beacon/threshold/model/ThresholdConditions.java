package com.fusionspy.beacon.threshold.model;

import java.math.BigDecimal;

/**
 * 阈值条件枚举类.
 * User: carvin
 * Date: 13-3-1
 * Time: 下午6:25
 */
public enum ThresholdConditions {

	EQ("=", "!=") {
        @Override
        boolean match(BigDecimal currentValue,BigDecimal thresholdValue) {
            return currentValue.compareTo(thresholdValue) == 0;
        }
    },
	LT("<", ">=") {
        @Override
        boolean match(BigDecimal currentValue,BigDecimal thresholdValue) {
            return currentValue.compareTo(thresholdValue) < 0;
        }
    },
	GT(">", "<=") {
        @Override
        boolean match(BigDecimal currentValue,BigDecimal thresholdValue) {
            return currentValue.compareTo(thresholdValue) > 0;
        }
    },
	LE("<=", ">") {
        @Override
        boolean match(BigDecimal currentValue,BigDecimal thresholdValue) {
            return currentValue.compareTo(thresholdValue) <= 0;
        }
    },
	GE(">=", "<") {
        @Override
        boolean match(BigDecimal currentValue,BigDecimal thresholdValue) {
            return currentValue.compareTo(thresholdValue) >= 0;
        }
    },
	NE("!=", "=") {
        @Override
        boolean match(BigDecimal currentValue,BigDecimal thresholdValue) {
            return currentValue.compareTo(thresholdValue) != 0;
        }
    };

	private String _symbol;
	private String _revertSymbol;

	private ThresholdConditions(String symbol, String revertSymbol) {
		this._symbol = symbol;
		this._revertSymbol = revertSymbol;
	}

	public String symbol() {
		return _symbol;
	}

	public String revertSymbol() {
		return _revertSymbol;
	}

    /**
     *
     * @param currentValue 当前值
     * @param thresholdValue 阈值
     * @return
     */
    abstract boolean match(BigDecimal currentValue,BigDecimal thresholdValue);

    public static ThresholdConditions instance(String value){
        return Enum.valueOf(ThresholdConditions.class,value);
    }

}
