/*
 * Created on 2019年9月15日
 */
package rough.preparation.batchjob;

import junit.framework.TestCase;

public class TestSimpleMultiThreadBatchJobExecutor extends TestCase {

    private SimpleMultiThreadBatchJobExecutor executor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        executor = new SimpleMultiThreadBatchJobExecutor();
    }

    public void testExecute() {
        BatchJob batchJob = new BatchJob();
        batchJob.setThreads(4);
        long current = System.currentTimeMillis();
        System.out.println("Start test.....");
        executor.execute(batchJob);
        System.out.println("End test, cost " + (System.currentTimeMillis() - current));
    }

}
