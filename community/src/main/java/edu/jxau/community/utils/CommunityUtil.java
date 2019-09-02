package edu.jxau.community.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;


import java.util.UUID;

/**
 * @title: community
 * @ClassName CommunityUtil.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
public class CommunityUtil {

    /**
     * 生成16位随机字符串
     * @return
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 对 message 进行MD5加密
     * @param message
     * @return
     */
    public static String md5(String message){
        if (StringUtils.isBlank(message)){
            return null;
        }
        return MD5.getInstance().getMD5(message);
    }
}
