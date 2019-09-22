/*
 * Created on 2013-4-1
 */
package rough.preparation.utils.batch;

import java.util.Map;

import rough.preparation.batch.MultiThreadBatchRunner;
import rough.preparation.batch.MultiThreadTaskHandler;
import rough.preparation.batch.MultiThreadTaskProvider;
import rough.preparation.batch.StepTaskProvider;

public class SleepBatchRunner extends MultiThreadBatchRunner<Integer, Object> {

    @Override
    protected MultiThreadTaskProvider<Integer> taskProvider(Map<String, String> parameter) {
        return new StepTaskProvider(1000, 0, 1);
    }

    @Override
    protected MultiThreadTaskHandler<Integer, Object> taskRunner(Map<String, String> parameter) {
        return new MultiThreadTaskHandler<Integer, Object>() {
            @Override
            protected Object doProcee(Integer taskObj) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                }
                return null;
            }
        };
    }

}
