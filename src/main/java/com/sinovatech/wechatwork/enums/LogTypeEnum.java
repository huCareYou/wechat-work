package com.sinovatech.wechatwork.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author by hukui
 * @Description 日志枚举
 * @Date 2019-07-03 15:40
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum LogTypeEnum {

    DEFAULT("DEFAULT","默认日志"),
    ACTIVE_STEP("ACTIVE_STEP","活动步骤监控日志"),
    API_MONITOR("API_MONITOR","第三方接口监控日志");
    ;

    @Setter
    private String LogType;
    @Setter
    private String LogTypeDesc;


}
