package com.sinovatech.wechatwork.constans;

/**
 * 常量
 */
public class Constans {

	/**
	 * 业务编码，本应用所有redis缓存key都应加上该前缀，以便区分。
	 */
	public static final String ACT_CODE = "wechat-work";


	public class RedisKey {


		/**
		 * 群发接入token
		 */
		public static final String GRROUP_SEND_TOKEN = ACT_CODE+"GRROUP_SEND_TOKEN";

	}

}
