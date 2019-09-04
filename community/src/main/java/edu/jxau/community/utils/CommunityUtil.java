package edu.jxau.community.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;


import java.util.Map;
import java.util.UUID;

/**
 * @title: community
 * @ClassName CommunityUtil.java
 * @Description: 社区业务中一些公共操作或非功能业务的抽取
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

    public static String getJSONString(int code, String msg, Map<String,Object> map){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        if (map != null){
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg){
        return getJSONString(code,msg,null);
    }

    public static String getJSONString(int code){
        return getJSONString(code,null,null);
    }
}
