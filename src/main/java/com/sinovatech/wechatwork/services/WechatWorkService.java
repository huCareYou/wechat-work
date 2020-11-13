package com.sinovatech.wechatwork.services;

import com.sinovatech.wechatwork.pojo.WeChatData;

/**
 * @author by hukui
 * @Description
 * @Date 2020/3/14 1:05 下午
 */
public interface WechatWorkService {

    String getGroudSendAccessToken();

    String groupSend(String token, WeChatData weChatData);
}
