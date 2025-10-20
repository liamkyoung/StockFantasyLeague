package Stock.Fantasy.League.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory cf) {
        return new StringRedisTemplate(cf);
    }

    @Bean
    public RedisTemplate<String, Object> jsonRedisTemplate(RedisConnectionFactory cf) {
        var t = new RedisTemplate<String, Object>();
        t.setConnectionFactory(cf);
        var ser = new GenericJackson2JsonRedisSerializer();
        t.setKeySerializer(new StringRedisSerializer());
        t.setValueSerializer(ser);
        t.setHashKeySerializer(new StringRedisSerializer());
        t.setHashValueSerializer(ser);
        t.afterPropertiesSet();
        return t;
    }
}
