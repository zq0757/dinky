package com.dlink.rpc;

import com.dlink.job.JobConfig;
import com.dlink.job.JobResult;
import com.dlink.result.IResult;
import com.dlink.result.SelectResult;
import com.dlink.result.SqlExplainResult;
import com.dlink.session.SessionConfig;
import com.dlink.session.SessionInfo;

import java.util.List;

/**
 * FlinkService
 *
 * @author wenmo
 * @since 2021/8/5 23:53
 */
public interface FlinkService {

    String getVersion();

    JobResult executeSql(JobConfig config, String statement);

    IResult executeDDL(JobConfig config, String statement);

    List<SqlExplainResult> explainSql(JobConfig config, String statement);

    SelectResult getJobData(String jobId);

    SessionInfo createSession(String session, SessionConfig sessionConfig, String createUser);

    List<SessionInfo> listSession(String createUser);

    List<SessionInfo> listAllSession();

    SessionInfo getSession(String session);

    boolean clearSession(String session);
}
