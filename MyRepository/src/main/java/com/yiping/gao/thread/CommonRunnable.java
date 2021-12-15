package com.yiping.gao.thread;

import com.yiping.gao.pojo.entity.User;

/**
 * @author 高一平
 * @date 2021/5/19
 * @description 自定义线程，保存当前登录用户，并可通过线程获取
 */
public class CommonRunnable implements Runnable {

    /**
     * 线程名称
     */
    private String runnableName;
    /**
     * 任务内容
     */
    private Runnable runnable;

    public CommonRunnable(User loginUser, Runnable runnable) {
        this.runnableName = ThreadUtils.setThreadUser(loginUser);
        this.runnable = runnable;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        String originName = Thread.currentThread().getName();
        Thread.currentThread().setName(originName + this.runnableName);
        this.runnable.run();
    }

}
