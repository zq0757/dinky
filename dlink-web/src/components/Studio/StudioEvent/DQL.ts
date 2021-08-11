import {getJobData} from "@/pages/FlinkSqlStudio/service";

export function showJobData(clusterId:number,jobId:string,dispatch:any) {
  const res = getJobData(clusterId,jobId);
  res.then((result)=>{
    dispatch&&dispatch({
      type: "Studio/saveResult",
      payload: result.datas,
    });
  });
}
