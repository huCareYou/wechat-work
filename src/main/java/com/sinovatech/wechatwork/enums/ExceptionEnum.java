package com.sinovatech.wechatwork.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author by hukui
 * @Description
 * @Date 2019-10-08 13:54
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    OK(200,"请求正常响应"),
    NOT_LOGIN(210,"用户未登录"),
    MISS_PARAMS(300,"参数错误"),
    ERROR(400,"非法的请求"),
    METHOD_NOT_SUPPORTED(998,"请求方式不支持"),
    SERVER_FAILED(999,"网络错误，待会重试");

    private Integer code;
    private String msg;

}
