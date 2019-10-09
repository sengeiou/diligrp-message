//package com.diligrp.message.config;
//
//import com.diligrp.message.component.MyStringRedisSerializer;
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//
//import java.lang.reflect.Method;
//
///**
// * <B>Description</B>
// * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
// * <B>农丰时代科技有限公司</B>
// *
// * @author yuehongbo
// * @date 2019/9/11 14:29
// */
//@EnableCaching
//@Configuration
//public class MyRedisCacheConfig {
//
//
//    @Autowired
//    private MyStringRedisSerializer myStringRedisSerializer;
//
//
//    /**
//     * KEY生成器用法:
//     * @return
//     * @Cacheable(value = "usercache",keyGenerator = "wiselyKeyGenerator")
//     */
//    @Bean
//    public KeyGenerator wiselyKeyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(target.getClass().getName());
//                sb.append(method.getName());
//                for (Object obj : params) {
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }
//
//    /**
//     * redis模板操作类,类似于jdbcTemplate的一个类;
//     * <p>
//     * 虽然CacheManager也能获取到Cache对象，但是操作起来没有那么灵活；
//     * <p>
//     * 这里在扩展下：RedisTemplate这个类不见得很好操作，我们可以在进行扩展一个我们
//     * <p>
//     * 自己的缓存类，比如：RedisStorage类;
//     *
//     * @return
//     */
//    @Bean("redisTemplate")
//    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        MyStringRedisSerializer stringRedisSerializer = new MyStringRedisSerializer();
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        redisTemplate.setKeySerializer(myStringRedisSerializer);
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//}
