/*
 * Created on 2013-4-1
 */
package rough.preparation.batch;

import java.util.Date;
import java.util.Map;

/**
 * 批处理运行器接口.
 * 
 * @author rough
 */
public interface BatchRunner {

    int STATUS_STOPPED = 0;
    int STATUS_STARTING = 1;
    int STATUS_STARTED = 2;
    int STATUS_PAUSING = 3;
    int STATUS_STOPPING = 4;

    /**
     * 运行批处理
     * @param parameter 运行参数, 只支持文本形式.
     */
    void run(Map<String, String> parameter);

    /**
     * 停止批处理.
     */
    void stop();

    /**
     * @return 当前状态.
     */
    int getStatus();

    /**
     * @return 最近一次启动时间.
     */
    Date getStarted();

    /**
     * @return 最近一次启动后已完成的任务数.
     */
    int getFinished();
}
