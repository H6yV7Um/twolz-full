package com.twolz.qiyi.web.security.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuwei
 * date: 16/8/29
 */
@Slf4j
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    //session 在redis过期时间是30分钟
    private static final int expireTime = 30;

    private static final String ACTIVE_SESSION = "shiro:session:";

    private RedisTemplate<String, Object> redisTemplate;

    public RedisSessionDAO(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        redisTemplate.opsForValue().set(ACTIVE_SESSION+sessionId.toString(), session, expireTime, TimeUnit.MINUTES);
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            session = (Session) redisTemplate.opsForValue().get(ACTIVE_SESSION + sessionId.toString());
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        String key = ACTIVE_SESSION + session.getId().toString();
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, session);
        }
        redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
    }

    // 删除session
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        redisTemplate.delete(ACTIVE_SESSION + session.getId().toString());
    }
}
