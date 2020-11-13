package com.sinovatech.wechatwork.utils;

import lombok.Data;

import java.io.Serializable;

/**
  * @Author: hukui
  * @Date: 2019-07-02
  * @Description: 统一响应对象
 */
@Data
public class ResponseResult implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 响应描述
     */
    private String msg;

    /**
     * 时间戳
     */
    private long time;

    /**
     * 携带数据
     */
    private Object data;


    public static ResponseResult build(Integer code, String msg, Object data) {
        return new ResponseResult(code, msg, data);
    }

    public static ResponseResult ok(Object data) {
        return new ResponseResult(data);
    }

    public static ResponseResult ok() {
        return new ResponseResult(null);
    }

    public ResponseResult() {
    }

    public static ResponseResult build(Integer code, String msg) {
        return new ResponseResult(code, msg, null);
    }

    public ResponseResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    public ResponseResult(Object data) {
        this.code = 200;
        this.msg = "OK";
        this.data = data;
        this.time = System.currentTimeMillis();
    }

}