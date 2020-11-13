package com.sinovatech.wechatwork.exception;

import com.alibaba.fastjson.JSON;
import com.sinovatech.wechatwork.enums.ExceptionEnum;
import com.sinovatech.wechatwork.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by hukui
 * @Description
 * @Date 2019-10-08 13:49
 */
@Slf4j
@ControllerAdvice
public class WebExceptionHandler {

    /**
     * 捕获自定义的异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(WebException.class)
    @ResponseBody
    public ResponseResult handleStudentException(HttpServletRequest request, WebException ex) {
        ResponseResult response;
        log.error("捕获自定义异常编码:{},响应消息:{}",ex.getResponse().getCode(),ex.getResponse().getMsg());
        response = ResponseResult.build(ex.getResponse().getCode(),ex.getResponse().getMsg());
        log.info("捕获自定义异常，响应页面：{}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 捕获非自定义的异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult handleException(HttpServletRequest request, Exception ex) {
        ResponseResult response;
        log.error("捕获非自定义异常。exception error:",ex);
        response = ResponseResult.build(ExceptionEnum.SERVER_FAILED.getCode(), ExceptionEnum.SERVER_FAILED.getMsg());
        log.info("捕获非自定义异常，响应页面：{}", JSON.toJSONString(response));
        return response;
    }


    /**
     * 捕获请求方法不匹配异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseResult methodNotSupportedException(HttpServletRequest request, Exception ex) {
        ResponseResult response;
        log.error("捕获请求方法不支持异常。exception error:",ex);
        response = ResponseResult.build(ExceptionEnum.METHOD_NOT_SUPPORTED.getCode(), ExceptionEnum.METHOD_NOT_SUPPORTED.getMsg());
        log.info("捕获请求方法不支持异常，响应页面：{}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 捕获请求参数缺失异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseResult missParamsException(HttpServletRequest request, Exception ex) {
        ResponseResult response;
        String requestUrl = request.getRequestURL().toString();
        log.error("请求:[{}]时捕获请求参数缺失异常。exception error:",requestUrl,ex);
        response = ResponseResult.build(ExceptionEnum.MISS_PARAMS.getCode(), ExceptionEnum.MISS_PARAMS.getMsg());
        log.info("捕获请求参数缺失异常，响应页面：{}", JSON.toJSONString(response));
        return response;
    }



}
