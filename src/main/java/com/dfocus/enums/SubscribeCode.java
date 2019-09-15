package com.dfocus.enums;

public enum SubscribeCode {
    SUB_SUCCESS("sub_success"), SUB_FAILED("sub_fail");

    private String code;

    private SubscribeCode(String code) {
        this.code = code;
    }

    public static SubscribeCode from(String code) {
        for (SubscribeCode c : SubscribeCode.values()) {
            if (c.code.equals(code)) {
                return c;
            }
        }
        return null;
    }

    public boolean equals(String code) {
        return this.code.equals(code);
    }

    public boolean equals(SubscribeCode code) {
        return this.code.equals(code.toString());
    }

    @Override
    public String toString() {
        return code;
    }
}