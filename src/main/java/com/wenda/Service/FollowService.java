package com.wenda.Service;

import com.wenda.Controller.HomeController;
import com.wenda.WendaUtills.JedisAdapter;
import com.wenda.WendaUtills.RedisKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @auther Zwh
 * @create 2019/6/30-20:04
 */
@Service
public class FollowService {
    private  static  final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    JedisAdapter jedisAdapter;


    public boolean addFollow(int userId,int entityType,int entityId) {
        String followerKey = RedisKeyUtils.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtils.getFolloweeKey(entityType, userId);
        Date date = new Date();

        Jedis jedis = jedisAdapter.getJedis();
        Transaction transaction = jedisAdapter.multi(jedis);

        transaction.zadd(followerKey,date.getTime(),String.valueOf(userId));
        transaction.zadd(followeeKey,date.getTime(),String.valueOf(entityId));

        List<Object> ret = jedisAdapter.exec(transaction, jedis);
        return (ret.size() == 2) && ((long)ret.get(0) > 0) && ((long)ret.get(1) > 0);
    }

    public boolean unFollow(int userId,int entityType,int entityId) {
        String followerKey = RedisKeyUtils.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtils.getFolloweeKey(entityType, userId);
        Date date = new Date();

        Jedis jedis = jedisAdapter.getJedis();
        Transaction transaction = jedisAdapter.multi(jedis);

        transaction.zrem(followerKey,String.valueOf(userId));
        transaction.zrem(followeeKey,String.valueOf(entityId));

        List<Object> ret = jedisAdapter.exec(transaction, jedis);
        return (ret.size() == 2) && ((long)ret.get(0) > 0) && ((long)ret.get(1) > 0);
    }

    private List<Integer> getIdsFromSet(Set<String> zrange){
        ArrayList<Integer> ids = new ArrayList<>();
        for (String id :zrange) {
            ids.add(Integer.valueOf(id));
        }
        return ids;
    }

    public List<Integer> getFollowers(int entityType,int entityId,int count){
        String followerKey = RedisKeyUtils.getFollowerKey(entityType, entityId);
        return  getIdsFromSet(jedisAdapter.zrange(followerKey, 0, count));
    }

    public List<Integer> getFollowers(int entityType,int entityId,int offset,int count){
        String followerKey = RedisKeyUtils.getFollowerKey(entityType, entityId);
        return  getIdsFromSet(jedisAdapter.zrange(followerKey, offset, count));
    }

    public List<Integer> getFollowees(int entityType,int userId,int count){
        String followeeKey = RedisKeyUtils.getFolloweeKey(entityType, userId);
        return  getIdsFromSet(jedisAdapter.zrevrange(followeeKey, 0, count));
    }

    public List<Integer> getFollowees(int entityType,int userId,int offset,int count){
        String followeeKey = RedisKeyUtils.getFolloweeKey(entityType, userId);
        return  getIdsFromSet(jedisAdapter.zrevrange(followeeKey, offset, count));
    }


    public long getFollowerCount(int entityType,int entityId){
        String followerKey = RedisKeyUtils.getFollowerKey(entityType, entityId);
        return jedisAdapter.zcard(followerKey);
    }

    public long getFolloweeCount(int entityType,int userId){
        String followeeKey = RedisKeyUtils.getFolloweeKey(entityType, userId);
        return jedisAdapter.zcard(followeeKey);
    }

    public boolean isFollower(int userId,int entityType,int entityId) {
        String followeeKey = RedisKeyUtils.getFolloweeKey(entityType, userId);
        return jedisAdapter.zscore(followeeKey, String.valueOf(entityId))!=null;
    }

}
