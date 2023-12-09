package com.yupi.springbootinit.manager;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AiManagerTest {
    @Resource
    private AiManager aiManager;

    @Test
    public void testNumUUID(){
        String s = RandomUtil.randomNumbers(12);
        System.out.println(s);
    }
    @Test
    void doChat() {

        String answer = aiManager.doChat(1690335817895030785L,"分析需求，\n" +
                "分析网站数据增长情况\n" +
                "原始数据,\n" +
                "日期，用户数\n" +
                "1号，10\n"+
                "2号，20\n"+
                "3号，30\n"
        );
        System.out.println(answer);
    }

}