package com.dlink.service;

import com.dlink.dto.SessionDTO;
import com.dlink.job.JobConfig;
import com.dlink.job.JobResult;
import com.dlink.result.IResult;
import com.dlink.result.SelectResult;
import com.dlink.result.SqlExplainResult;
import com.dlink.session.SessionConfig;
import com.dlink.session.SessionInfo;

import java.util.List;

/**
 * JobService
 *
 * @author wenmo
 * @since 2021/8/11 22:02
 */
public interface JobService {

    JobResult executeSql(JobConfig config,String statement);

    IResult executeDDL(JobConfig config,String statement);

    List<SqlExplainResult> explainSql(JobConfig config,String statement);

    SelectResult getJobData(Integer clusterId,String jobId);

    SessionInfo createSession(String session, SessionConfig sessionConfig, String createUser);

    List<SessionInfo> listSession(String createUser);

    List<SessionInfo> listAllSession();

    SessionInfo getSession(String session);

    boolean clearSession(String session);
}
