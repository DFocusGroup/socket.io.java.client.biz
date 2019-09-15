package com.dfocus.enums;

public enum AuthCode {
    AUTH_SUCCESS("auth_success"), AUTH_FAILED("auth_fail");

    private String code;

    private AuthCode(String code) {
        this.code = code;
    }

    public static AuthCode from(String code) {
        for (AuthCode c : AuthCode.values()) {
            if (c.code.equals(code)) {
                return c;
            }
        }
        return null;
    }

    public boolean equals(String code) {
        return this.code.equals(code);
    }

    public boolean equals(AuthCode code) {
        return this.code.equals(code.toString());
    }

    @Override
    public String toString() {
        return code;
    }
}