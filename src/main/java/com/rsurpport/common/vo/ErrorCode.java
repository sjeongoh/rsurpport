package com.rsurpport.common.vo;

public enum ErrorCode {

    SUCCESS(0, "SUCCESS")
    , ERROR(1, "ERROR") ;

    private int code;
    private String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}


