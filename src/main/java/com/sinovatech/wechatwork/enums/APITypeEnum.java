package com.sinovatech.wechatwork.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author by hukui
 * @Description api类型枚举
 * @Date 2019-07-03 15:40
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum APITypeEnum {

    API_listTaskInfo("listTaskInfo","查询任务详情"),
    API_doTask("doTask","完成任务"),
    API_inAccountBooks("inAccountBooks","奖励积分入账");

    @Setter
    private String apiType;
    @Setter
    private String apiDesc;


}
