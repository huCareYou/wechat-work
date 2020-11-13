package com.sinovatech.wechatwork;

import com.sinovatech.wechatwork.pojo.WeChatData;
import com.sinovatech.wechatwork.properties.WechatWorkProperties;
import com.sinovatech.wechatwork.services.WechatWorkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sinovatech.wechatwork.constans.Constans.RedisKey.GRROUP_SEND_TOKEN;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WechatWorkApplicationTests {
    @Autowired
    private WechatWorkService wechatWorkService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private WechatWorkProperties properties;

    @Test
    public void contextLoads() {
        String token = jedisCluster.get(GRROUP_SEND_TOKEN);
        if(StringUtils.isBlank(token)){
            token = wechatWorkService.getGroudSendAccessToken();
            jedisCluster.setex(GRROUP_SEND_TOKEN,7200,token);
            log.info("企业微信群发接入token放入缓存,key=[{}],value=[{}].7200秒后失效",GRROUP_SEND_TOKEN,token);
        }
        WeChatData weChatData = new WeChatData();
        weChatData.setTouser("@all");
        weChatData.setAgentid(properties.getGroupSendAgentId());
        weChatData.setMsgtype("text");
        Map map = new HashMap();
        map.put("content","测试企业微信应用消息推送api实现.当前时间:"+new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(new Date()));
        weChatData.setText(map);
        weChatData.setSafe(0);
        String s = wechatWorkService.groupSend(token, weChatData);
        log.info("返回:[{}]",s);
    }



}
