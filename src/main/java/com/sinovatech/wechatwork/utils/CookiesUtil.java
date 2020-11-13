package com.sinovatech.wechatwork.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookiesUtil {

    /**
     * 写入cookies
     * 
     * @param key
     * @param descMobileAndPwdStr
     * @param response
     */
    public static void addCookies(String key, String descMobileAndPwdStr, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, descMobileAndPwdStr);
        cookie.setDomain("client.10010.com");
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    public static void addCookiesLuckDraw(String key, String descMobileAndPwdStr,
			HttpServletResponse response) {
		Cookie cookie = new Cookie(key, descMobileAndPwdStr);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
	}

    /**
     * 得到cookies
     * @param key
     * @param request
     * @return
     */
    public static String getCookiseValue(String key, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String value = "";
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (key.equalsIgnoreCase(c.getName())) {
                    value = c.getValue();
                }
            }
        }
        return value;
    }

    public static void delCookies(String key, HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        try {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (key.equalsIgnoreCase(c.getName())) {
                    c.setMaxAge(0);
                    //根据你创建cookie的路径进行填写
                    c.setPath("/");
                    response.addCookie(c);
                }
            }
        } catch (Exception ex) {
        }

    }
}
