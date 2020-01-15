/*
 * Created on 2019年9月15日
 */
package rough.preparation.batchjob;

import junit.framework.TestCase;
import lombok.extern.apachecommons.CommonsLog;

import java.math.BigDecimal;

@CommonsLog
public class TestSimpleMultiThreadBatchJobExecutor extends TestCase {

    private SimpleMultiThreadBatchJobExecutor executor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        executor = new SimpleMultiThreadBatchJobExecutor();
    }

    public void testExecute() {
        BatchJob batchJob = new BatchJob();
        batchJob.setThreads(1);
        batchJob.setMaxInvokes(new BigDecimal(100));
        long current = System.currentTimeMillis();
        log.info("Start test.....");
        executor.execute(batchJob);
        log.info("End test, cost " + (System.currentTimeMillis() - current));

        batchJob.setThreads(4);
        current = System.currentTimeMillis();
        log.info("Start test.....");
        executor.execute(batchJob);
        log.info("End test, cost " + (System.currentTimeMillis() - current));
    }

}
