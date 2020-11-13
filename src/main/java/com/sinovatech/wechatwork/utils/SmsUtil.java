package com.sinovatech.wechatwork.utils;

import com.sinovatech.ntf.send.NtfplatService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsUtil {

	/**
	 * 发送短信
	 * @param tel 目标手机号码
	 * @param msg 短信内容
	 * @return
	 */
	public static int sendSms(String tel, String msg,String bizcode) {
		log.info("{}进入发送短信方法,短信内容{},bizcode={}",tel,msg,bizcode);
		int flag ;
		try {
			flag = NtfplatService.sendSingle("11007", "其他短信类下发", "中国联通", msg, tel);
			log.info("{}发送短信结果:{}",tel, flag == 0 ? "成功":"失败");
		} catch (Exception e) {
			flag = 2;
			log.error( "{}短信发送异常：" ,tel, e);
		}
		return flag;
	}
}
