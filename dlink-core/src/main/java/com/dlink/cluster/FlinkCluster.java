package com.dlink.cluster;

import com.dlink.api.FlinkAPI;

/**
 * FlinkCluster
 *
 * @author wenmo
 * @since 2021/5/25 15:08
 **/
public class FlinkCluster {

    public static String testFlinkJobManagerAddress(String addressStr,String address) {
        try {
            String version = FlinkAPI.build(address).getVersion();
            if (!version.isEmpty()) {
                return address;
            }
        } catch (Exception e) {
        }
        String[] servers = addressStr.split(",");
        for (String server : servers) {
            try {
                String res = FlinkAPI.build(server).getVersion();
                if (!res.isEmpty()) {
                    return server;
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
}
