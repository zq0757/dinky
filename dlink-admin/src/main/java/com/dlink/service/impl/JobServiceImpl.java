package com.dlink.service.impl;

import com.dlink.assertion.Asserts;
import com.dlink.job.JobConfig;
import com.dlink.job.JobResult;
import com.dlink.model.Cluster;
import com.dlink.result.IResult;
import com.dlink.result.SelectResult;
import com.dlink.result.SqlExplainResult;
import com.dlink.rpc.FlinkService;
import com.dlink.rpc.RPCManager;
import com.dlink.rpc.RPCProperties;
import com.dlink.service.ClusterService;
import com.dlink.service.JobService;
import com.dlink.session.SessionConfig;
import com.dlink.session.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JobServiceImpl
 *
 * @author wenmo
 * @since 2021/8/11 22:03
 */
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private ClusterService clusterService;
    @Autowired
    private RPCProperties rpcProperties;

    private FlinkService buildFlinkService(JobConfig config){
        if(!config.isUseSession()) {
            config.setAddress(clusterService.buildEnvironmentAddress(config.isUseRemote(), config.getClusterId()));
        }
        Cluster cluster = clusterService.getById(config.getClusterId());
        String clientUrl = rpcProperties.getFlinkClientUrl(cluster.getVersion());
        return RPCManager.createServer(clientUrl, cluster.getVersion());
    }

    private FlinkService buildFlinkService(Integer clusterId){
        Cluster cluster = clusterService.getById(clusterId);
        String clientUrl = rpcProperties.getFlinkClientUrl(cluster.getVersion());
        return RPCManager.createServer(clientUrl, cluster.getVersion());
    }

    @Override
    public JobResult executeSql(JobConfig config, String statement) {
        return buildFlinkService(config).executeSql(config,statement);
    }

    @Override
    public IResult executeDDL(JobConfig config, String statement) {
        return buildFlinkService(config).executeDDL(config,statement);
    }

    @Override
    public List<SqlExplainResult> explainSql(JobConfig config, String statement) {
        return buildFlinkService(config).explainSql(config,statement);
    }

    @Override
    public SelectResult getJobData(Integer clusterId, String jobId) {
        return buildFlinkService(clusterId).getJobData(jobId);
    }

    @Override
    public SessionInfo createSession(String session, SessionConfig sessionConfig, String createUser) {
        return buildFlinkService(sessionConfig.getClusterId()).createSession(session,sessionConfig,createUser);
    }

    @Override
    public List<SessionInfo> listSession(String createUser) {
        List<SessionInfo> sessionInfos = new ArrayList<>();
        Map<String, FlinkService> servers = RPCManager.listServer();
        for(Map.Entry<String,FlinkService> entry: servers.entrySet()){
            sessionInfos.addAll(entry.getValue().listSession(createUser));
        }
        return sessionInfos;
    }

    @Override
    public List<SessionInfo> listAllSession() {
        List<SessionInfo> sessionInfos = new ArrayList<>();
        Map<String, FlinkService> servers = RPCManager.listServer();
        for(Map.Entry<String,FlinkService> entry: servers.entrySet()){
            sessionInfos.addAll(entry.getValue().listAllSession());
        }
        return sessionInfos;
    }

    @Override
    public boolean clearSession(String session) {
        List<SessionInfo> sessionInfos = listAllSession();
        for (int i = 0; i < sessionInfos.size(); i++) {
            if(Asserts.isEquals(sessionInfos.get(i).getSession(),session)){
                buildFlinkService(sessionInfos.get(i).getSessionConfig().getClusterId()).clearSession(session);
                return true;
            }
        }
        return false;
    }
}
