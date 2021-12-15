package com.yiping.gao.message.sender;

import com.yiping.gao.message.model.Message;

/**
 * @author 高一平
 * @date 2021/5/20
 * @description 消息发送接口
 */
public interface MessageSender {
    /**
     * 消息发送
     *
     * @param address 发送地址
     * @param message 发送消息
     */
    void send(String address, Message message);
}
