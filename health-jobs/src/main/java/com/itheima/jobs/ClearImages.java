package com.itheima.jobs;


import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Set;


@Component
public class ClearImages {

    @Autowired
    JedisPool jedisPool;

    public void run(){

        //数据多的key放前面
        Set<String> needDeleteImg = jedisPool.getResource().
                sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        for (String img : needDeleteImg) {
            //删除七牛
            QiniuUtil.delete(img);
            //删除redis里面，避免下次再匹配到
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,img);
        }

    }
}
