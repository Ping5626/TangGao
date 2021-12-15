package com.yiping.gao.message;

import com.yiping.gao.message.model.Message;
import com.yiping.gao.message.sender.MessageSender;
import com.yiping.gao.message.sender.SmsMessageSender;

/**
 * @author 高一平
 * @date 2021/5/20
 * @description 提醒
 */
public class Notification {
    /**
     * 消息发送组件
     */
    private MessageSender messageSender;

    public Notification(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * 消息发送
     *
     * @param address
     * @param message
     */
    public void sendMessage(String address, Message message) {
        this.messageSender.send(address, message);
    }

    public static void main(String[] args) {
        MessageSender sender = new SmsMessageSender();
        Notification notification = new Notification(sender);
        notification.sendMessage("123456", new Message());
    }

}
