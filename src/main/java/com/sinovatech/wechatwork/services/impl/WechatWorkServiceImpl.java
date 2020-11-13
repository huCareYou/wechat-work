package com.sinovatech.wechatwork.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinovatech.wechatwork.pojo.WeChatData;
import com.sinovatech.wechatwork.properties.WechatWorkProperties;
import com.sinovatech.wechatwork.services.WechatWorkService;
import com.sinovatech.wechatwork.utils.NewHttpCilentUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author by hukui
 * @Description
 * @Date 2020/3/14 1:15 下午
 */
@Slf4j
@Service
public class WechatWorkServiceImpl implements WechatWorkService {

    @Autowired
    private WechatWorkProperties wechatWorkProperties;

    @Override
    public String getGroudSendAccessToken() {
        String qiyeId = wechatWorkProperties.getQiyeId();
        String secret = wechatWorkProperties.getGroupSendSecret();
        String getToken = wechatWorkProperties.getGetToken();
        StringBuffer stringBuffer = new StringBuffer(getToken);
        stringBuffer.append("?");
        stringBuffer.append("corpid=");
        stringBuffer.append(qiyeId);
        stringBuffer.append("&corpsecret=");
        stringBuffer.append(secret);
        String url = stringBuffer.toString();
        long start = System.currentTimeMillis();
        String result = NewHttpCilentUtil.doGet(url, null);
        log.info("获取企业微信-群发接入token调用接口[{}],响应[{}]耗时:{}ms",url,result, System.currentTimeMillis()-start);
        String token = null;
        if(StringUtils.isNotBlank(result)){
            JSONObject jsonObject = JSON.parseObject(result);
            String errcode = jsonObject.get("errcode").toString();
            String errmsg = jsonObject.get("errmsg").toString();
            String access_token = jsonObject.get("access_token").toString();
            String expires_in = jsonObject.get("expires_in").toString();
            if(StringUtils.equals(errcode,"0")){
                token = access_token;
            }else{
                log.info("获取企业微信-群发接入token失败,失败原因[{}]",errmsg);
            }
        }else{
            log.info("获取企业微信-群发接入token调用接口未响应");
        }
        return token;
    }

    @Override
    public String groupSend(String token,WeChatData weChatData) {
        String groupSend = wechatWorkProperties.getGroupSend();
        StringBuffer stringBuffer = new StringBuffer(groupSend);
        stringBuffer.append("?access_token=");
        stringBuffer.append(token);
        groupSend = stringBuffer.toString();
        long start = System.currentTimeMillis();
        String result = NewHttpCilentUtil.doPost(groupSend, JSON.toJSONString(weChatData),"utf-8");
        log.info("企业微信-应用推送消息接口[{}],传参[{}],响应[{}]耗时:{}ms",groupSend, JSON.toJSONString(weChatData),result, System.currentTimeMillis()-start);
        return result;
    }
}
