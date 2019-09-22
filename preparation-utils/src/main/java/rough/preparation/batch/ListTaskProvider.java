/*
 * Created on 2013-4-1
 */
package rough.preparation.batch;

import java.util.Collections;
import java.util.List;

/**
 * 使用列表实现的批处理任务分发器.
 * 
 * @author rough
 */
public class ListTaskProvider<E> extends AbstractTaskProvider<E> {

    private int next;

    private List<E> tasks;

    /**
     * Constructor.
     * 
     * @param tasks 需要执行的批处理任务.
     */
    public ListTaskProvider(List<E> tasks) {
        if (tasks == null)
            this.tasks = Collections.emptyList();
        else
            this.tasks = tasks;

        next = 0;
    }

    /**
     * @see rough.preparation.batch.AbstractTaskProvider#doGetNextTask()
     */
    protected E doGetNextTask() {

        if (next < tasks.size())
            return tasks.get(next++);
        else
            return null;
    }

}
