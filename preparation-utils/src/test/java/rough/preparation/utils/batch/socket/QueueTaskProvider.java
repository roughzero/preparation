/*
 * Created on 2018年1月2日
 */
package rough.preparation.utils.batch.socket;

import java.util.Queue;

import rough.preparation.batch.MultiThreadTaskProvider;

public class QueueTaskProvider implements MultiThreadTaskProvider<SocketWrapper> {
    private Queue<SocketWrapper> queue;
    int providedTasks = 0;

    public QueueTaskProvider(Queue<SocketWrapper> queue) {
        this.queue = queue;
    }

    public SocketWrapper nextTask() {
        SocketWrapper result = queue.poll();
        if (result == null) {
            return new SocketWrapper(null);
        } else {
            providedTasks++;
            return result;
        }
    }

    public int providedTasks() {
        return providedTasks;
    }

}
