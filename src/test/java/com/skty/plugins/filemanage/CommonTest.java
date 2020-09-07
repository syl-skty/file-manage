package com.skty.plugins.filemanage;

import com.skty.plugins.filemanage.test.TestInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhaoyun
 * @date 2020/9/7 18:42
 */
public class CommonTest extends BaseTest{

    @Autowired
    private TestInterface testInterface;

    @Test
    public void test(){
        testInterface.test("我是测试");
    }
}
