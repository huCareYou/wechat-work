package com.sinovatech.wechatwork.pojo;

import lombok.Data;

/**
 * @author by hukui
 * @Description
 * @Date 2020/3/14 4:06 下午
 */
@Data
public class WeChatData {
    private String touser;
    private String toparty;
    private String totag;
    private String msgtype;
    private int agentid;
    /**
     * 文本消息
     */
    private Object text;
    /**
     * 图片消息
     */
    private Object image;
    /**
     * 语音消息
     */
    private Object voice;
    /**
     * 视频消息
     */
    private Object video;
    /**
     * 文件消息
     */
    private Object file;
    /**
     * 文本卡片
     */
    private Object textcard;
    /**
     * 图文
     */
    private Object news;
    /**
     * 图文
     */
    private Object mpnews;
    /**
     * markdown语法支持
     */
    private Object markdown;
    /**
     * 小程序
     */
    private Object miniprogram_notice;
    /**
     * 任务卡
     */
    private Object taskcard;
    private int safe;
    private int enable_id_trans;
    private int enable_duplicate_check;
    private int duplicate_check_interval = 1800;
}
