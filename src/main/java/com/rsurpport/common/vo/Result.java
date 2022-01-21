package com.rsurpport.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;

@Data
public class Result {


    private int code;
    private String message;
    private Object result = new HashMap();
    @JsonIgnore
    private ErrorCode errorCode;

    public void setCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
    }

}
