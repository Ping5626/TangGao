package com.yiping.gao.jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 高一平
 * @date 2021/5/20
 * @description 测试用例【控制反转】
 */
@Slf4j
public abstract class TestCase {

    /**
     * 测试
     *
     * @return
     */
    public abstract boolean doTest();

    /**
     * 运行测试用例
     */
    public void run() {
        if (doTest()) {
            log.info("测试成功");
        } else {
            log.error("测试失败");
        }
    }

}
