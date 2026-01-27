package com.hope.xueling.xueling.english;

import cn.hutool.core.util.ReUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XuelingEnglishApplicationTests {

    @Test
    void contextLoads() {
        String str = "Hello我是谁？";
        String str1 = String.join("", ReUtil.findAll("[\\u4e00-\\u9fa5]", str, 0));
        System.out.println(str1);
        String str2 = String.join("",ReUtil.findAll("[a-zA-Z]", str, 0));
        System.out.println(str2);
    }

}
