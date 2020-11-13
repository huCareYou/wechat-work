package com.sinovatech.wechatwork.exception;


import com.sinovatech.wechatwork.enums.ExceptionEnum;

/**
 * @author by hukui
 * @Description
 * @Date 2019-10-08 13:53
 */
public class WebException extends RuntimeException {

    private final ExceptionEnum response;

    public WebException(ExceptionEnum response) {
        this.response = response;
    }
    public ExceptionEnum getResponse() {
        return response;
    }

}
