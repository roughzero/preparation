package rough.preparation.utils;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

public class ObjectMatcherTest {
    private static final int TEST_COUNT = 100;
    protected Log logger = LogFactory.getLog(getClass());

    @Test
    public void testMatch() {
        CodeMatcher codeMatcher = new CodeMatcher(YearMatcher.class);
        StopWatch watch = new StopWatch();
        watch.start();
        for (int i = 0; i < TEST_COUNT; i++) {
            for (int j = 0; j < TEST_COUNT; j++) {
                TestResultObject testResultObject = new TestResultObject(Integer.toString(i), Integer.toString(j), Integer.toString(i * j));
                codeMatcher.put(testResultObject);
            }
        }
        watch.stop();
        logger.info("Test cache finished, cost: " + watch.getTime());
        watch.reset();
        watch.start();
        for (int i = 0; i < TEST_COUNT; i++) {
            for (int j = 0; j < TEST_COUNT; j++) {
                TestQueryObject queryObject = new TestQueryObject(i + "," + j, Integer.toString(i), Integer.toString(j));
                TestResultObject resultObject = codeMatcher.get(queryObject);
                if (resultObject == null) {
                    logger.error(queryObject);
                }
                Assert.assertNotNull(resultObject);
                Assert.assertEquals(Integer.toString(i * j), resultObject.getRate());
            }
        }
        watch.stop();
        logger.info("Test query finished, cost: " + watch.getTime());
    }
}
