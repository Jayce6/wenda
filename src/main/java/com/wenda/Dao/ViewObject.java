package com.wenda.Dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @auther 张伟豪
 * @create 2019/6/23-19:13
 */
public class ViewObject {
    Map<String,Object> vos = new HashMap<>();

    public  void SetKey(String key,Object values){
        vos.put(key,values);
    }

    public Object get(String key){
        return vos.get(key);
    }
 }
