package com.dlink.rpc;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.dlink.assertion.Asserts;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * RPCManager
 *
 * @author wenmo
 * @since 2021/8/8 21:29
 */
public class RPCManager {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo_client.xml");
    private static Map<String,FlinkService> servers = new HashMap<>();

    public static FlinkService getServer(String serverAddress){
        Asserts.checkNullString(serverAddress,"服务地址不能为空");
        return servers.get(serverAddress);
    }

    public static boolean existServer(String serverAddress){
        Asserts.checkNullString(serverAddress,"服务地址不能为空");
        return servers.containsKey(serverAddress);
    }

    public static Map<String,FlinkService> listServer(){
        return servers;
    }

    public static FlinkService createServer(String serverAddress,String version){
        version = version.substring(0,version.lastIndexOf("."));
        if(existServer(serverAddress)){
            return getServer(serverAddress);
        }
        String url = "dubbo://"+serverAddress+"/com.dlink.rpc.FlinkService";//更改不同的Dubbo服务暴露的ip地址&端口
        ReferenceBean<FlinkService> referenceBean = new ReferenceBean<>();
        referenceBean.setApplicationContext(context);
        referenceBean.setInterface(com.dlink.rpc.FlinkService.class);
        referenceBean.setUrl(url);
        referenceBean.setVersion(version);
        referenceBean.setTimeout(600000);
        try {
            referenceBean.afterPropertiesSet();
            FlinkService service = referenceBean.get();
            servers.put(version,service);
            return service;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
