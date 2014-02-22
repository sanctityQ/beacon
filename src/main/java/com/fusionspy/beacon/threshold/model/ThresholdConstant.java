package com.fusionspy.beacon.threshold.model;


import org.apache.commons.lang.StringUtils;

public class ThresholdConstant {


    enum Type{PATTERN,NUMERIC}

    enum Pattern{

        CONTAINS("CT"){
            @Override
            public boolean match(String currentValue,String thresholdValue){
                return StringUtils.contains(thresholdValue,currentValue);
            }
        },
        NOT_CONTAINS("DC"){
            @Override
            boolean match(String currentValue, String thresholdValue) {
                return !StringUtils.contains(thresholdValue,currentValue);
            }
        },
        EQUAL("QL") {
            @Override
            boolean match(String currentValue, String thresholdValue) {
                for(String v:StringUtils.splitByWholeSeparator(thresholdValue,",")){
                    if(StringUtils.equals(currentValue,v))
                        return true;
                }
                return false;
            }
        },
        NOT_EQUAL("NQ") {
            @Override
            boolean match(String currentValue, String thresholdValue) {
                for(String v:StringUtils.splitByWholeSeparator(thresholdValue,",")){
                    if(!StringUtils.equals(currentValue,v))
                        return true;
                }
                return false;
            }
        },
        START_WITH("SW") {
            @Override
            boolean match(String currentValue, String thresholdValue) {
                for(String v:StringUtils.splitByWholeSeparator(thresholdValue,",")){
                    if(StringUtils.startsWith(currentValue,v))
                        return true;
                }
                return false;
            }
        },
        END_WITH("EW") {
            @Override
            boolean match(String currentValue, String thresholdValue) {
                for(String v:StringUtils.splitByWholeSeparator(thresholdValue,",")){
                    if(StringUtils.endsWith(currentValue,v))
                        return true;
                }
                return false;
            }
        };

        private final String value;

        Pattern(String value) {
             this.value = value;
        }

        public String getValue(){
            return this.value;
        }

        public static ThresholdConstant.Pattern instance(String value){
            if(value.equals("CT"))
                return ThresholdConstant.Pattern.CONTAINS;
            else if(value.equals("DC"))
                return  ThresholdConstant.Pattern.NOT_CONTAINS;
            else if(value.equals("QL"))
                return   ThresholdConstant.Pattern.EQUAL;
            else if(value.equals("NQ"))
                return   ThresholdConstant.Pattern.NOT_EQUAL;
            else if(value.equals("SW"))
                return   ThresholdConstant.Pattern.START_WITH;
            else if(value.equals("EW"))
                return   ThresholdConstant.Pattern.END_WITH;
            else
                throw new IllegalArgumentException("param value is:"+value);
        }

        /**
         *
         * @param currentValue 当前值
         * @param thresholdValue 阈值
         * @return
         */
        abstract boolean match(String currentValue,String thresholdValue);

        String symbol(){
           switch (this){
               case CONTAINS:return "包括";
               case NOT_CONTAINS:return "不包括";
               case EQUAL:return "等于";
               case NOT_EQUAL:return "不等于";
               case START_WITH:return "开始于";
               case END_WITH:return "结束于";
           }
           return null;
        }

    }
}
