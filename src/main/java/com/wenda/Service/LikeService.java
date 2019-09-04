package com.wenda.Service;

import com.wenda.Controller.HomeController;
import com.wenda.WendaUtills.JedisAdapter;
import com.wenda.WendaUtills.RedisKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther 张伟豪
 * @create 2019/6/29-14:22
 */
@Service
public class LikeService {
    private  static  final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    JedisAdapter jedisAdapter;

    public long getLikeCount(int entityType,int entityId){
        String likeKey = RedisKeyUtils.getLikeKey(entityType,entityId);
        return jedisAdapter.scard(likeKey);

    }

    public int getLikeStatus(int userID,int entityType,int entityId){
            String likeKey = RedisKeyUtils.getLikeKey(entityType,entityId);
            if (jedisAdapter.sismember(likeKey,String.valueOf(userID))){
                return 1;
            }
            String dislikeKey = RedisKeyUtils.getDislikeKey(entityType,entityId);
             return jedisAdapter.sismember(dislikeKey,String.valueOf(userID))?-1:0;
    }


    public long like(int userID,int entityType,int entityId){

        String likeKey = RedisKeyUtils.getLikeKey(entityType,entityId);
        jedisAdapter.sadd(likeKey,String.valueOf(userID));

        String dislikeKey = RedisKeyUtils.getDislikeKey(entityType,entityId);
        jedisAdapter.srem(dislikeKey,String.valueOf(userID));
        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userID,int entityType,int entityId){
        String dislikeKey = RedisKeyUtils.getDislikeKey(entityType,entityId);
        jedisAdapter.sadd(dislikeKey,String.valueOf(userID));

        String likeKey = RedisKeyUtils.getLikeKey(entityType,entityId);
        jedisAdapter.srem(likeKey,String.valueOf(userID));

        return jedisAdapter.scard(likeKey);
    }

}
