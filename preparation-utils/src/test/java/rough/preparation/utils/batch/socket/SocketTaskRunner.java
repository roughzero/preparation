/*
 * Created on 2018年1月2日
 */
package rough.preparation.utils.batch.socket;

import java.util.Map;
import java.util.Queue;

import rough.preparation.batch.MultiThreadBatchRunner;
import rough.preparation.batch.MultiThreadTaskHandler;
import rough.preparation.batch.MultiThreadTaskProvider;

public class SocketTaskRunner extends MultiThreadBatchRunner<SocketWrapper, Boolean> {
    private final MultiThreadTaskProvider<SocketWrapper> provider;

    public SocketTaskRunner(Queue<SocketWrapper> queue) {
        this.provider = new QueueTaskProvider(queue);
    }

    @Override
    protected MultiThreadTaskProvider<SocketWrapper> taskProvider(Map<String, String> parameter) {
        return this.provider;
    }

    @Override
    protected MultiThreadTaskHandler<SocketWrapper, Boolean> taskRunner(Map<String, String> parameter) {
        return new SocketTaskHandler();
    }

}
