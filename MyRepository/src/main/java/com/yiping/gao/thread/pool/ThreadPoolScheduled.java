package com.yiping.gao.thread.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author 高一平
 * @date 2021/5/19
 * @description
 */
@Slf4j
@Component
public class ThreadPoolScheduled {

    @Scheduled(cron = "* */1 * * * ?")
    public void runTask() {
        LinkedBlockingDeque<Runnable> queue = CommonThreadPool.queue;
        if (!queue.isEmpty()) {
            try {
                Runnable task = queue.takeFirst();
                CommonThreadPool.getPool().execute(task);
            } catch (InterruptedException e) {
                log.error("任务执行失败！", e);
            }
        }
    }

}
