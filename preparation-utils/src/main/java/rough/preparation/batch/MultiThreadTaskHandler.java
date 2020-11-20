/*
 * Created on 2011-10-18
 */
package rough.preparation.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 为多线程批处理服务的任务处理器.
 * 
 * @author rough
 */
public abstract class MultiThreadTaskHandler<Task, Result> implements Runnable {
    protected static final Log logger = LogFactory.getLog(MultiThreadTaskHandler.class);
    /** 批处理运行器 */
    protected MultiThreadBatchRunner<Task, Result> batchRunner;

    private boolean isClosed = false;
    private boolean toRemove = false;

    /**
     * @return 是否已关闭, 关闭表示已处理完成任务.
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * @param batchRunner 批处理运行器
     */
    public void setBatchRunner(MultiThreadBatchRunner<Task, Result> batchRunner) {
        this.batchRunner = batchRunner;
        setup();
    }

    /**
     * 从运行中取消.
     */
    public synchronized void removeFromJob() {
        toRemove = true;
    }

    /**
     * 执行业务逻辑
     * @see java.lang.Runnable#run()
     */
    public final void run() {
        while (true) {
            if (toRemove) {
                close();
                batchRunner.removeThread(this);
                break;
            }

            Task task = getTask();

            if (task != null) {

                batchRunner.doBeforeTask(task);

                Result result = null;

                try {
                    result = doProcee(task);
                } catch (RuntimeException e) {
                    logger.error("Get RuntimeException when process task.", e);
                } finally {
                    batchRunner.doAfterTask(task, result);
                }

            } else {
                close();
                batchRunner.doAfterProcess(this);
                break;
            }
        }
    }

    /**
     * 执行实际的业务逻辑.
     * @param task 任务对象.
     * @return 执行结果.
     */
    protected abstract Result doProcee(Task task);

    /**
     * 取得下一个任务.
     * @return 下一个任务.
     * @since 0.5.4 将此方法独立处理以便控制取得任务的节奏.
     */
    protected Task getTask() {
        return batchRunner.nextTask();
    }

    /**
     * 初始设置.
     */
    @SuppressWarnings("EmptyMethod")
    protected void setup() {
    }

    /**
     * 关闭任务, 若 Overwrite 此方法, 需将 super.close() 放在最后!
     */
    protected void close() {
        isClosed = true;
    }
}
