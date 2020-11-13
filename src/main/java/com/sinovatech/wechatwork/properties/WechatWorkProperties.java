package com.sinovatech.wechatwork.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author by hukui
 * @Description
 * @Date 2020/3/14 1:02 下午
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat-work")
public class WechatWorkProperties {

    private String qiyeId;
    private int groupSendAgentId;
    private String groupSendSecret;
    private String getToken;
    private String groupSend;
}
