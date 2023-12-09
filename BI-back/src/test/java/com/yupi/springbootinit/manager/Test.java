package com.yupi.springbootinit.manager;

import cn.hutool.core.util.RandomUtil;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test {
    @org.junit.jupiter.api.Test
    public void ran(){
        System.out.println(RandomUtil.randomInt(10000000,100000000));
    }
}
