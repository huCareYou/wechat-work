package com.sinovatech.wechatwork.task;

import com.alibaba.fastjson.JSON;
import com.sinovatech.wechatwork.pojo.WeChatData;
import com.sinovatech.wechatwork.properties.WechatWorkProperties;
import com.sinovatech.wechatwork.services.WechatWorkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sinovatech.wechatwork.constans.Constans.RedisKey.GRROUP_SEND_TOKEN;

/**
 * @author by hukui
 * @Description
 * @Date 2020/3/14 1:28 下午
 */
@Component
@Slf4j
public class WeChatWorkTask {

    @Autowired
    private WechatWorkService wechatWorkService;

    @Autowired
    private WechatWorkProperties wechatWorkProperties;

    @Autowired
    private JedisCluster jedisCluster;

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void getGroupSendToken(){
        log.info("进入定时任务=====获取企业微信群发接入token");
        String token = jedisCluster.get(GRROUP_SEND_TOKEN);
        if(StringUtils.isBlank(token)){
            token = wechatWorkService.getGroudSendAccessToken();
            if(StringUtils.isNotBlank(token)){
                jedisCluster.setex(GRROUP_SEND_TOKEN,7200,token);
                log.info("企业微信群发接入token放入缓存,key=[{}],value=[{}].7200秒后失效",GRROUP_SEND_TOKEN,token);
            }
        }else{
            log.info("当前token[{}]未失效",token);
        }
        log.info("结束定时任务=====获取企业微信群发接入token");
    }


    @Scheduled(cron = "0 0/20 * * * ? ")
    public void testGroupSend(){
        log.info("进入定时任务=====企业微信群发");
        String token = jedisCluster.get(GRROUP_SEND_TOKEN);
        if(StringUtils.isBlank(token)){
            log.info("使用token时失效,重新获取");
            token = wechatWorkService.getGroudSendAccessToken();
            if(StringUtils.isNotBlank(token)){
                jedisCluster.setex(GRROUP_SEND_TOKEN,7200,token);
                log.info("企业微信群发接入token放入缓存,key=[{}],value=[{}].7200秒后失效",GRROUP_SEND_TOKEN,token);
            }
        }
        int groupSendAgentId = wechatWorkProperties.getGroupSendAgentId();
        String msg = "这是自定义应用推送消息-测试定时发送,当前时间:"+new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(new Date());
        WeChatData weChatData = new WeChatData();
        weChatData.setTouser("@all");
        weChatData.setAgentid(groupSendAgentId);
        weChatData.setMsgtype("text");
        Map map = new HashMap<String,String>();
        StringBuffer stringBuffer = new StringBuffer(msg);
        stringBuffer.append("<a href=\"http://mail.sinovatech.com/sso3/POST\">");
        stringBuffer.append("公司主页");
        stringBuffer.append("</a>");
        map.put("content",stringBuffer.toString());
        weChatData.setText(map);
        weChatData.setSafe(0);
        String s = wechatWorkService.groupSend(token,weChatData);
        log.info("结束定时任务=====企业微信群发");
    }
}
