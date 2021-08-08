package com.dlink.rpc;

import com.dlink.assertion.Asserts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RPCProperties
 *
 * @author wenmo
 * @since 2021/8/8 21:52
 */
@Data
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "dlink")
public class RPCProperties {
    private List<FlinkClient> clients;

    @Data
    public static class FlinkClient {
        private String version;
        private String url;
    }

    public String getFlinkClientUrl(String version){
        version = version.substring(0,version.lastIndexOf("."));
        for(FlinkClient client : clients){
            if(Asserts.isEquals(version,client.version)){
                return client.getUrl();
            }
        }
        return null;
    }
}
