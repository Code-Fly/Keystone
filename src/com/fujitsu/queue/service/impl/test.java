package com.fujitsu.queue.service.impl;

import com.fujitsu.keystone.publics.entity.push.response.NewsMessage;
import com.fujitsu.keystone.publics.service.impl.MessageService;

/**
 * Created by Barrie on 15/12/18.
 */
public class test {
    public static void main(String[] arg) {
        NewsMessage newsMessage = new NewsMessage();
        System.out.println(11);
        String msg = MessageService.messageToXml(newsMessage);
        System.out.println(msg);
    }
}
