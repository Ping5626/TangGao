package com.yiping.gao.thread.pool;

import io.micrometer.core.instrument.util.NamedThreadFactory;

import java.util.concurrent.*;

/**
 * @author 高一平
 * @date 2021/5/19
 * @description 线程池，自定义拒绝策略
 */
public class CommonThreadPool {

    /**
     * 线程池
     */
    private static ThreadPoolExecutor pool;
    /**
     * 待执行队列
     */
    protected static LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();

    /**
     * 单例模式，私有化构造函数
     */
    private CommonThreadPool() {
    }

    /**
     * 懒加载
     * 获取线程池
     *
     * @return
     */
    public static ThreadPoolExecutor getPool() {
        if (pool == null) {
            int cores = Runtime.getRuntime().availableProcessors();
            pool = new ThreadPoolExecutor(cores / 4, cores/2, 5, TimeUnit.MINUTES,
                    new SynchronousQueue<>(), new NamedThreadFactory("COMMON"), new CommonRejectedExecutionHandler());
        }
        return pool;
    }

    /**
     * 自定义拒绝策略
     * 当任务数超出线程池最大线程数，将超出部分加入待执行队列
     */
    private static class CommonRejectedExecutionHandler implements RejectedExecutionHandler {
        /**
         * Method that may be invoked by a {@link ThreadPoolExecutor} when
         * {@link ThreadPoolExecutor#execute execute} cannot accept a
         * task.  This may occur when no more threads or queue slots are
         * available because their bounds would be exceeded, or upon
         * shutdown of the Executor.
         *
         * <p>In the absence of other alternatives, the method may throw
         * an unchecked {@link RejectedExecutionException}, which will be
         * propagated to the caller of {@code execute}.
         *
         * @param r        the runnable task requested to be executed
         * @param executor the executor attempting to execute this task
         * @throws RejectedExecutionException if there is no remedy
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                queue.putLast(r);
            } catch (InterruptedException e) {
                throw new RejectedExecutionException("Task " + r.toString() +
                        " rejected from " + e);
            }
        }
    }

}
