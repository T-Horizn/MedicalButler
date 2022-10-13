package com.offcn;

import com.offcn.config.MessageConstant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @author zh
 * @Description TODO
 * @createTime 2022/8/15
 */
@SpringBootTest
public class Test1 {

    @Test
    void test1(){

        System.out.println(UUID.randomUUID());
    }
}
