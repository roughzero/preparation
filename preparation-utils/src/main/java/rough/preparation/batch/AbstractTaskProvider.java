/*
 * Created on 2011-10-20
 */
package rough.preparation.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 任务分发器基类.
 * 注意这个类不是线程安全的!
 * 
 * @author rough
 */
public abstract class AbstractTaskProvider<E> implements MultiThreadTaskProvider<E> {
    protected static Log logger = LogFactory.getLog(AbstractTaskProvider.class);

    private int proviedTasks = 0;

    /**
     * @see rough.preparation.batch.MultiThreadTaskProvider#nextTask()
     */
    public E nextTask() {
        E nextTask = doGetNextTask();
        if (nextTask != null)
            proviedTasks++;
        return nextTask;
    }

    /**
     * @see rough.preparation.batch.MultiThreadTaskProvider#providedTasks()
     */
    public int providedTasks() {
        return proviedTasks;
    }

    /**
     * 由继承的类实现的实际取得下一个任务的方法.
     * @return 下一个任务.
     */
    protected abstract E doGetNextTask();
}
