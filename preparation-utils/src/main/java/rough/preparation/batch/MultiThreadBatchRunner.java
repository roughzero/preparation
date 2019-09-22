/*
 * Created on 2013-3-28
 */
package rough.preparation.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 多线程批处理运行器实现基类.
 * 
 * TODO 使用方法
 * @author rough
 */
public abstract class MultiThreadBatchRunner<Task, Result> implements BatchRunner {

    /** 默认线程数 */
    private static final int DEFAULT_THREADS = 1;
    /** 最小线程数 */
    private static final int MIN_THREADS = 1;
    /** 最大线程数 */
    private static final int MAX_THREADS = 10;
    /** 默认输出运行信息的任务间隔数 */
    private static final int DEFAULT_DEBUG_COUNT = 1000;

    /** 在参数中传入的线程数 Key 值 */
    public static final String KEY_COUNT_OF_THREADS = MultiThreadBatchRunner.class.getSimpleName() + ".countOfThreads";

    protected Log logger = LogFactory.getLog(getClass());

    private int status = STATUS_STOPPED;

    /**
     * @see rough.preparation.batch.BatchRunner#getStatus()
     */
    public int getStatus() {
        return this.status;
    }

    /** 任务分发器, 实现任务的分发 */
    private MultiThreadTaskProvider<Task> taskProvider = null;

    /** 线程池, 保存正在运行的线程 */
    private Map<MultiThreadTaskHandler<Task, Result>, Thread> runnerMap = new HashMap<MultiThreadTaskHandler<Task, Result>, Thread>();

    private Date started = null;

    /**
     * @see rough.preparation.batch.BatchRunner#getStarted()
     */
    public Date getStarted() {
        return started;
    }

    private int finished = 0;

    /**
     * @see rough.preparation.batch.BatchRunner#getFininshed()
     */
    public int getFininshed() {
        return finished;
    }

    /** 本次执行使用的最大线程数 */
    protected int maxThreads = 0;

    /** 输出运行信息的任务间隔数 */
    private int debugCount = DEFAULT_DEBUG_COUNT;

    public int getDebugCount() {
        return debugCount;
    }

    public void setDebugCount(int debugCount) {
        this.debugCount = debugCount;
    }

    /**
     * @return 当前的运行线程数
     */
    public int currentCountThreads() {
        return runnerMap.size();
    }

    /**
     * 根据参数取得线程运行数.
     * @param parameter 执行参数.
     * @return 线程运行数.
     * @see #KEY_COUNT_OF_THREADS
     */
    protected static int getCountOfThreads(Map<String, String> parameter) {
        String s = parameter.get(KEY_COUNT_OF_THREADS);
        if (StringUtils.isEmpty(s))
            return DEFAULT_THREADS;
        int threads = DEFAULT_THREADS;
        try {
            threads = Integer.valueOf(s);
        } catch (Exception e) {
            return DEFAULT_THREADS;
        }
        threads = threads < MIN_THREADS ? MIN_THREADS : threads;
        threads = threads > MAX_THREADS ? MAX_THREADS : threads;
        return threads;
    }

    /**
     * @see rough.preparation.batch.BatchRunner#run(java.util.Map)
     */
    public void run(Map<String, String> parameter) {
        synchronized (this) {
            if (STATUS_STOPPED != getStatus())
                throw new AlreadyStartedException();

            reset();

            status = STATUS_STARTING;
        }

        taskProvider = taskProvider(parameter);

        int threads = getCountOfThreads(parameter);

        for (int i = 0; i < threads; i++) {
            this.addThread(taskRunner(parameter));
        }
    }

    /**
     * Publish next task for thread to run, null if all task is finished.
     * 发布下一个任务供线程执行, 若所有任务已完成则返回空.
     * @return next task. Null if all task is finished.
     */
    public synchronized Task nextTask() {
        switch (getStatus()) {
            case STATUS_STOPPED:
            case STATUS_STOPPING:
                return null;
            default:
                Task result = taskProvider.nextTask();
                if (getDebugCount() != 0 && result != null && taskProvider.providedTasks() % getDebugCount() == 0) {
                    logger.info(taskProvider.providedTasks() + " tasks provided.");
                }
                return result;
        }
    }

    /**
     * 增加一个线程.
     * @param handler task handler.
     */
    public synchronized void addThread(MultiThreadTaskHandler<Task, Result> handler) {
        if (this.taskProvider == null) {
            logger.warn("No task provider.");
            return;
        }

        if (currentCountThreads() >= MAX_THREADS) { // 限制不能超过最大线程数
            logger.warn("Already added to max threads: " + MAX_THREADS);
            return;
        }

        if (STATUS_STARTED != getStatus()) {
            status = STATUS_STARTED;
            doBeforeProcess();
        }
        handler.setBatchRunner(this);
        Thread thread = new Thread(handler);
        this.runnerMap.put(handler, thread);
        if (maxThreads < this.runnerMap.size())
            maxThreads = this.runnerMap.size();
        thread.start();
    }

    /**
     * 移去一个线程.
     * @param runner task runner.
     */
    public synchronized void removeThread(MultiThreadTaskHandler<Task, Result> runner) {
        if (runnerMap.containsKey(runner)) {
            if (runner.isClosed())
                runnerMap.remove(runner);
            else
                runner.removeFromJob();
        }
        if (runnerMap.isEmpty()) {
            doAfterProcess();
        }
    }

    /**
     * @see rough.preparation.batch.BatchRunner#stop()
     */
    public synchronized void stop() {
        switch (this.status) {
            case STATUS_STARTED:
            case STATUS_PAUSING:
                this.status = STATUS_STOPPING;
                logger.info("Stopping batch...");
                break;
            default:
                break;
        }
    }

    /**
     * 根据参数取得任务分发器.
     * @param parameter 运行参数.
     * @return 任务分发器.
     */
    protected abstract MultiThreadTaskProvider<Task> taskProvider(Map<String, String> parameter);

    /**
     * 根据参数取得任务处理器.
     * @param parameter 运行参数.
     * @return 任务处理器.
     */
    protected abstract MultiThreadTaskHandler<Task, Result> taskRunner(Map<String, String> parameter);

    /**
     * 第一个线程开始启动前的处理.
     */
    protected void doBeforeProcess() {
        started = new Date(System.currentTimeMillis());
        logger.info("Job started.");
    }

    /**
     * Callback for start single task.
     * 开始单个任务的回调.
     * @param task task object.
     */
    public synchronized void doBeforeTask(Task task) {
    }

    /**
     * Callback for finish single task.
     * 完成单个任务的回调.
     * @param task task object.
     * @param result result of task.
     */
    public synchronized void doAfterTask(Task task, Result result) {
        finished++;
    }

    /**
     * Callback for finish thread Job.
     * 完成所有任务后线程结束前的回调.
     * @param runner task runner.
     */
    public synchronized void doAfterProcess(MultiThreadTaskHandler<Task, Result> runner) {
        removeThread(runner);
    }

    /**
     * 所有任务完成, 线程关闭后的处理.
     */
    protected synchronized void doAfterProcess() {
        logger.info("Job finished! cost " + (double) (System.currentTimeMillis() - started.getTime()) / 1000 + " seconds."
                + " finish " + finished + " tasks. max active threads: " + maxThreads);
        this.status = STATUS_STOPPED;
        this.taskProvider = null;
    }

    /**
     * 重置相关信息.
     */
    protected void reset() {
        this.finished = 0;
        this.maxThreads = 0;
        this.runnerMap.clear();
        this.started = null;
    }
}
