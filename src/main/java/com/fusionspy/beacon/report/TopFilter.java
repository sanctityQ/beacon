package com.fusionspy.beacon.report;


public enum TopFilter {
    five {
        @Override
        public String description() {
            return "前5位";
        }

        @Override
        public int getTopNum() {
            return 5;
        }
    },ten {
        @Override
        public String description() {
            return "前10位";
        }

        @Override
        public int getTopNum() {
            return 10;
        }
    };

    public abstract String description();

    public abstract int getTopNum();
}
