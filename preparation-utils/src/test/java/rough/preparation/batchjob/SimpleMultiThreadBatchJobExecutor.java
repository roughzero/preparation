/*
 * Created on 2019年9月15日
 */
package rough.preparation.batchjob;

import java.util.ArrayList;
import java.util.List;

public class SimpleMultiThreadBatchJobExecutor
        extends AbstractMultiThreadBatchJobExecutor<Integer, Boolean> {

    @Override
    protected List<Integer> taskParams(BatchJob batchJob) {
        List<Integer> results = new ArrayList<Integer>();
        for (int i = 0; i < 9999; i++) {
            results.add(i);
        }
        return results;
    }

    @Override
    protected Boolean doTaskLogic(BatchJob batchJob, Integer t) {
        if (t.intValue() == 97) {
            String message = "97 is not supported, t = " + t;
            logger.error(message);
            throw new TaskException(message);
        }

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            // do nothing.
        }

        return true;
    }

}
