/*
 * Created on 2019年9月15日
 */
package rough.preparation.batchjob;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.time.StopWatch;

public abstract class AbstractMultiThreadBatchJobExecutor<TaskParam, TaskResult>
        extends AbstractBatchJobExecutor {

    private static final int DEFAULT_THREADS = 1;
    private static final int DEFAULT_MAX_INVOKES = 1000;

    /**
     * @see rough.preparation.batchjob.BatchJobExecutor#execute(rough.preparation.batchjob.BatchJob)
     */
    public String execute(final BatchJob batchJob) {
        // Create thread pool.
        int successed = 0;
        int failed = 0;
        StopWatch stopWatch = new StopWatch();
        int threads = batchJob.getThreads() > 0 ? batchJob.getThreads() : DEFAULT_THREADS;
        int maxInvokes;
        if (batchJob.getMaxInvokes() == null) {
            maxInvokes = DEFAULT_MAX_INVOKES;
        } else {
            maxInvokes = batchJob.getMaxInvokes().intValue() > 0
                    ? batchJob.getMaxInvokes().intValue()
                    : DEFAULT_MAX_INVOKES;
        }
        ExecutorService executorPool = Executors.newFixedThreadPool(threads);
        logger.info("Created thread pool with threads: " + threads);

        try {
            // Get task parameters.
            stopWatch.start();
            List<TaskParam> taskParams = taskParams(batchJob);
            StringBuilder info = new StringBuilder();
            info.append("Get task parameters successful");
            info.append(", cost: " + stopWatch.getTime() / 1000 + " seconds.");
            info.append(", tasks: " + taskParams.size());
            info.append(".");
            logger.info(info);

            // 根据 MAX_INVOKE_TASKS 分批执行任务
            int times = taskParams.size() / maxInvokes;
            if (times * maxInvokes < taskParams.size()) {
                times++;
            }
            for (int time = 0; time < times; time++) {
                StopWatch taskWatch = new StopWatch();
                taskWatch.start();
                List<Callable<TaskResult>> tasks = createTasks(batchJob, taskParams, time,
                        maxInvokes);
                try {
                    List<Future<TaskResult>> results = executorPool.invokeAll(tasks);
                    for (Future<TaskResult> future : results) {
                        try {
                            future.get();
                            successed++;
                        } catch (ExecutionException e) {
                            // 如果是可控的异常则不记录，由实现程序记录。
                            failed++;
                            if (!(e.getCause() instanceof BatchJobException)) {
                                logger.error(e, e);
                            }
                        } catch (Throwable e) {
                            failed++;
                            logger.error(e, e);
                        }
                    }
                } catch (Throwable e) {
                    logger.warn(e, e);
                }
                // 每个批次执行完成后输出日志
                taskWatch.stop();
                StringBuilder message = new StringBuilder();
                message.append("Time: " + (time + 1) + " finished");
                message.append(", current tasks: " + tasks.size());
                message.append(
                        ", finished tasks: " + (successed + failed) + "/" + taskParams.size());
                message.append(", cost: " + taskWatch.getTime() + " milliseconds");
                message.append(".");
                logger.info(message);
            }

            // Show result's info.
            stopWatch.stop();
            StringBuilder resultMessage = new StringBuilder();
            resultMessage.append("Batch job finishes, tasks: " + taskParams.size());
            resultMessage.append(", successed: " + successed);
            resultMessage.append(", failed: " + failed);
            resultMessage.append(", all cost: " + stopWatch.getTime() + " milliseconds");
            resultMessage.append(".");
            logger.info(resultMessage);

            return resultMessage.toString();
        } finally {
            executorPool.shutdown(); // close resources.
        }
    }

    /**
     * 取得批处理任务参数
     * @param batchJob 批处理
     * @return 批处理任务参数列表
     */
    protected abstract List<TaskParam> taskParams(BatchJob batchJob);

    /**
     * 执行批处理任务逻辑模板，主要解决异常的处理
     * @param batchJob 批处理
     * @param taskParam 任务参数
     * @return 任务结果
     */
    protected TaskResult doTask(BatchJob batchJob, TaskParam taskParam) {
        try {
            return doTaskLogic(batchJob, taskParam);
        } catch (Throwable e) {
            String message = "Catch exception when run task: " + taskParam;
            logger.error(message);
            logger.error(e, e);
            if (e instanceof BatchJobException) {
                throw (BatchJobException) e;
            } else {
                throw new RuntimeException(message);
            }
        }
    }

    /**
     * 执行批处理任务逻辑
     * @param batchJob 批处理
     * @param taskParam 任务参数
     * @return 任务结果
     */
    protected abstract TaskResult doTaskLogic(BatchJob batchJob, TaskParam taskParam);

    /**
     * 创建需要执行的任务，每批最大为 MAX_INVOKE_TASKS
     * @param batchJob 批处理
     * @param taskParams 任务参数
     * @param time 批次
     * @param maxInvokes 最大同时调用数
     * @return 需要执行的任务列表
     */
    private List<Callable<TaskResult>> createTasks(final BatchJob batchJob,
            List<TaskParam> taskParams, int time, int maxInvokes) {
        List<Callable<TaskResult>> tasks = new ArrayList<Callable<TaskResult>>();
        for (int i = 0; i < maxInvokes; i++) {
            int index = time * maxInvokes + i;
            if (index >= taskParams.size()) {
                break;
            }
            final TaskParam taskParam = taskParams.get(index);
            tasks.add(new Callable<TaskResult>() {
                public TaskResult call() throws Exception {
                    return doTask(batchJob, taskParam);
                }
            });
        }
        return tasks;
    }

}
