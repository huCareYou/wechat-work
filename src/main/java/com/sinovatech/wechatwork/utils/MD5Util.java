package com.sinovatech.wechatwork.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * @Description  MD5加密工具类
 * @Author hukui
 * @Date 2018/11/7 8:39 PM
 */
public class MD5Util {

	static Logger log = LoggerFactory.getLogger(MD5Util.class);

	/**
	 *
	 * @param str	需要加密的字符串
	 * @param isUpper 	字母大小写(false为默认小写，true为大写)
	 * @param bit 	加密的类型（16,32,64）
	 * @return
	 */
	public static String getMD5(String str, boolean isUpper, Integer bit) {
		String md5 = new String();
		try {
			// 创建加密对象
			MessageDigest md = MessageDigest.getInstance("md5");
			if (bit == 64) {
				BASE64Encoder bw = new BASE64Encoder();
				String bsB64 = bw.encode(md.digest(str.getBytes("utf-8")));
				md5 = bsB64;
			} else {
				// 计算MD5函数
				md.update(str.getBytes());
				byte b[] = md.digest();
				int i;
				StringBuffer sb = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0){
						i += 256;
					}
					if (i < 16){
						sb.append("0");
					}
					sb.append(Integer.toHexString(i));
				}
				md5 = sb.toString();
				if(bit == 16) {
					//截取32位md5为16位
					String md16 = md5.substring(8, 24).toString();
					md5 = md16;
					if (isUpper){
						md5 = md5.toUpperCase();
					}
					return md5;
				}
			}
			//转换成大写
			if (isUpper){
				md5 = md5.toUpperCase();
			}
		} catch (Exception e) {
			log.info("MD5加密异常",e);
		}

		return md5;
	}

}
