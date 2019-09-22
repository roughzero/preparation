/*
 * Created on 2011-10-20
 */
package rough.preparation.batch;

/**
 * 为多线程批处理服务的任务分发器.
 * 
 * @author rough
 */
public interface MultiThreadTaskProvider<E> {
    /**
     * @return 下一个任务对象.
     */
    E nextTask();

    /**
     * @return 已分发的任务数量.
     */
    int providedTasks();
}
