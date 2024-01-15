package com.yiping.gao.tests;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 高一平
 * @date 2021/5/20
 * @description 测试
 */
public class TestApplication {

    private static final List<TestCase> cases = new ArrayList<>();

    /**
     * 添加测试用例
     *
     * @param test
     */
    public static void register(TestCase test) {
        cases.add(test);
    }

    /**
     * 测试
     *
     * @param args
     */
    public static final void main(String[] args) {
        for (TestCase test : cases) {
            test.run();
        }
    }

}
