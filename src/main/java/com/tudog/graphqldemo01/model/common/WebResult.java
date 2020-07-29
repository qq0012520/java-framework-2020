package com.tudog.graphqldemo01.model.common;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class WebResult {
    private boolean success;

    private Integer code;

    private String msg;

    public WebResult(boolean success){
        this.success = success;
        this.code = null;
        this.msg = null;
    }

    public WebResult(boolean success,Integer code){
        this.success = success;
        this.code = code;
        this.msg = null;
    }

    public WebResult(boolean success,String msg){
        this.success = success;
        this.code = null;
        this.msg = msg;
    }

}