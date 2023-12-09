package com.yupi.springbootinit;

import cn.hutool.http.HttpRequest;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class TestRedis {
    public static void main(String[] args) {
        System.out.println("skkdk");
        String url = "https://api.openai.com/v1/models";
        String result = HttpRequest.post(url)
                .header("Authorization", "Bearer " + "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")
                .body("讲一个笑话")
                .execute()
                .body();
        System.out.println("skkdk"+result);
    }
    @Test
    public void testRedis1() {
//        RedisURI uri = new RedisURI("localhost", 6379, 60, TimeUnit.SECONDS);

        RedisProperties.Lettuce lettuce = new RedisProperties.Lettuce();
        Jedis jedis = new Jedis("114.115.151.230", 6379, 60000);
//        jedis.auth("");
        jedis.set("name", "Tom");
        jedis.set("age", "18");
        System.out.println(jedis.get("wkf") + jedis.get("age"));
    }
    @Test
    public void testSetGet() throws Exception {
        RedisURI redisUri = RedisURI.builder()                    // <1> 创建单机连接的连接信息
                .withHost("114.115.151.230")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        RedisClient redisClient = RedisClient.create(redisUri);   // <2> 创建客户端
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        // <3> 创建线程安全的连接
        RedisCommands<String, String> redisCommands = connection.sync();
        // <4> 创建同步命令
        SetArgs setArgs = SetArgs.Builder.nx().ex(500);
        String result = redisCommands.set("name", "用户信息", setArgs);
        Assertions.assertThat(result).isEqualToIgnoringCase("OK");
        result = redisCommands.get("name");
        Assertions.assertThat(result).isEqualTo("throwable");
        // ... 其他操作
        connection.close();   // <5> 关闭连接
        redisClient.shutdown();  // <6> 关闭客户端
    }

    @Test
    public String ai() {
        System.out.println("skkdk");
        String url = "https://api.openai.com/v1/models";
        String result = HttpRequest.post(url)
                .header("Authorization", "Bearer " + "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")
                .body("讲一个笑话")
                .execute()
                .body();
        System.out.println("skkdk");
        return "result";
    }
}