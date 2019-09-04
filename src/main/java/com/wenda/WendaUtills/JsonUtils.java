package com.wenda.WendaUtills;

import com.alibaba.fastjson.JSONObject;

/**
 * @auther zwh
 * @create 2019/6/26-16:00
 */
public class JsonUtils {


    public static  String getJSONString(int code,String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        return jsonObject.toJSONString();
    }
    public static  String getJSONString(int code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        return jsonObject.toJSONString();
    }
}
