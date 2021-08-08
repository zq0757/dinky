package com.dlink.rpc;

import com.dlink.job.JobConfig;
import com.dlink.job.JobManager;
import com.dlink.job.JobResult;
import com.dlink.result.IResult;
import com.dlink.result.SelectResult;
import com.dlink.result.SqlExplainResult;

import java.util.List;

/**
 * FlinkServiceImpl
 *
 * @author wenmo
 * @since 2021/8/5 23:54
 */
public class FlinkServiceImpl implements FlinkService {
    @Override
    public String getVersion() {
        return "1.12";
    }

    @Override
    public JobResult executeSql(JobConfig config, String statement) {
        JobManager jobManager = JobManager.build(config);
        return jobManager.executeSql(statement);
    }

    @Override
    public IResult executeDDL(JobConfig config, String statement) {
        JobManager jobManager = JobManager.build(config);
        return jobManager.executeDDL(statement);
    }

    @Override
    public List<SqlExplainResult> explainSql(JobConfig config, String statement) {
        JobManager jobManager = JobManager.build(config);
        return jobManager.explainSql(statement);
    }

    @Override
    public SelectResult getJobData(String jobId) {
        return JobManager.getJobData(jobId);
    }
}
