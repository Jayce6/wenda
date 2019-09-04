package com.wenda.async;

import com.alibaba.fastjson.JSON;
import com.wenda.WendaUtills.JedisAdapter;
import com.wenda.WendaUtills.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther Zwh
 * @create 2019/6/30-10:01
 */
@Service
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){

        try {
            String jsonString = JSON.toJSONString(eventModel);
            String eventQueueKey = RedisKeyUtils.getEventQueueKey();
            jedisAdapter.lpush(eventQueueKey,jsonString);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

}
