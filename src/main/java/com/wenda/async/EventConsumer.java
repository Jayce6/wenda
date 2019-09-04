package com.wenda.async;

import com.alibaba.fastjson.JSON;
import com.wenda.Controller.HomeController;
import com.wenda.WendaUtills.JedisAdapter;
import com.wenda.WendaUtills.JsonUtils;
import com.wenda.WendaUtills.RedisKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther Zwh
 * @create 2019/6/30-10:20
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private  static  final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private Map<EventType, List<EventHandler>> config = new HashMap<>();

    private ApplicationContext applicationContext;


    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> beans= applicationContext.getBeansOfType(EventHandler.class);
        if (beans!=null) {
            for (Map.Entry<String, EventHandler> entry :beans.entrySet()) {
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for (EventType type:eventTypes){
                    if (!config.containsKey(type))
                    {
                        config.put(type,new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                while (true) {
                    String Key = RedisKeyUtils.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(Key);

                    for (String message : events) {
                        if (message.equals(Key)) {
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(message, EventModel.class);
                        if (!config.containsKey(eventModel.getEventType())) {
                                logger.error("不能识别的事件类型");
                            }

                            for (EventHandler eventHandler : config.get(eventModel.getEventType())) {
                                eventHandler.doHandler(eventModel);

                        }

                }

                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
