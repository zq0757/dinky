package com.dlink;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Server
 *
 * @author wenmo
 * @since 2021/8/5 23:57
 */

public class Server {
    public static void main(String[] args) throws Exception {
        // 加载配置文件
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:dubbo_server.xml");
        ctx.start();
        System.err.println("启动了.....");
        System.in.read();
    }
}

