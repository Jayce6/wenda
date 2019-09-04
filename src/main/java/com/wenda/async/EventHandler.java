package com.wenda.async;

import java.util.List;

/**
 * @auther Zwh
 * @create 2019/6/30-10:18
 */
public interface EventHandler {

    void doHandler(EventModel eventModel);

    List<EventType> getSupportEventTypes();

}
