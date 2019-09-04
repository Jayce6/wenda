package com.wenda.WendaUtills;

import com.wenda.Controller.HomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import sun.reflect.generics.scope.Scope;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @auther 张伟豪
 * @create 2019/6/29-13:05
 */
@Service
public class JedisAdapter implements InitializingBean {
    private  static  final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private JedisPool jedisPool;

    @Override
    public void afterPropertiesSet() throws Exception {
         jedisPool = new JedisPool("redis://192.168.111.132:6379/1");
    }

    public long sadd(String key,String value){
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        } finally {
            if (jedis!=null)  {
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key){
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        }catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        } finally {
            if (jedis!=null)  {
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key,String value){
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        } finally {
            if (jedis!=null)  {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key,String value){
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        } finally {
            if (jedis!=null)  {
                jedis.close();
            }
        }
        return false;
    }
    public long  lpush(String key,String value){
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key,value);
        }catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        } finally {
            if (jedis!=null)  {
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> brpop(String key){
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            return jedis.brpop(0,key);
        }catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        } finally {
            if (jedis!=null)  {
                jedis.close();
            }
        }
        return null;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();

    }

   public Transaction multi(Jedis jedis) {
           return jedis.multi();
   }

   public List<Object> exec(Transaction tan,Jedis jedis){
       try {
           return tan.exec();
       }catch (Exception e) {
           logger.error("发生异常"+e.getMessage());
       }finally {
           if (tan!=null) {
               try {
                   tan.close();
               } catch (IOException e) {
                   logger.error("发生异常1"+e.getMessage());
               }
           }
           if (jedis!=null) {
               jedis.close();
           }
       }
       return null;
   }

  public  long zadd(String key,double score,String value) {
      Jedis jedis =null;
        try {
            jedis = jedisPool.getResource();

            return jedis.zadd(key,score,value);
      }catch (Exception e) {
          logger.error("发生异常2"+e.getMessage());
      }finally {
          if (jedis!=null) {
              jedis.close();
          }
      }
      return 0;
  }
    public Set<String> zrange(String key, int start , int end) {
        Jedis jedis =null;
        try {
            jedis = jedisPool.getResource();

            return jedis.zrange(key,start,end);
        }catch (Exception e) {
            logger.error("发生异常3"+e.getMessage());
        }finally {
            if (jedis!=null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrevrange(String key, int start , int end) {
        Jedis jedis =null;
        try {
            jedis = jedisPool.getResource();

            return jedis.zrevrange(key,start,end);
        }catch (Exception e) {
            logger.error("发生异常4"+e.getMessage());
        }finally {
            if (jedis!=null) {
                jedis.close();
            }
        }
        return null;
    }
    public long zcard(String key) {
        Jedis jedis =null;
        try {
            jedis = jedisPool.getResource();

            return jedis.zcard(key);
        }catch (Exception e) {
            logger.error("发生异常5"+e.getMessage());
        }finally {
            if (jedis!=null) {
                jedis.close();
            }
        }
        return 0;
    }

    public Double zscore(String key,String member) {
        Jedis jedis =null;
        try {
            jedis = jedisPool.getResource();

            Double zscore = jedis.zscore(key, member);
            return zscore;
        }catch (Exception e) {
            logger.error("发生异常6"+e.getMessage());
        }finally {
            if (jedis!=null) {
                jedis.close();
            }
        }
        return null;
    }
    public long zrem(String key,String value){
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrem(key,value);
        }catch (Exception e) {
            logger.error("发生异常"+e.getMessage());
        } finally {
            if (jedis!=null)  {
                jedis.close();
            }
        }
        return 0;
    }
  }



