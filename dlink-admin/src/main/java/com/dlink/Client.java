package com.dlink;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.dlink.rpc.FlinkService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO
 *
 * @author wenmo
 * @since 2021/7/31 20:29
 */
public class Client {
    public static void main(String[] args) {
        String url = "dubbo://localhost:20112/com.dlink.rpc.FlinkService";//更改不同的Dubbo服务暴露的ip地址&端口
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:dubbo_client.xml");

        ReferenceBean<FlinkService> referenceBean = new ReferenceBean<>();
        referenceBean.setApplicationContext(ctx);
        referenceBean.setInterface(com.dlink.rpc.FlinkService.class);
        referenceBean.setUrl(url);
        referenceBean.setVersion("1.12");

        try {
            referenceBean.afterPropertiesSet();
            FlinkService service = referenceBean.get();
            System.out.print(service.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
