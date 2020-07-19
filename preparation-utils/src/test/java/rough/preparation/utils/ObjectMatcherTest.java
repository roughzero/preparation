package rough.preparation.utils;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

public class ObjectMatcherTest {
    protected Log logger = LogFactory.getLog(getClass());

    @Test
    public void testMatch() {
        CodeMatcher codeMatcher = new CodeMatcher(YearMatcher.class);
        StopWatch watch = new StopWatch();
        watch.start();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                TestResultObject testResultObject = new TestResultObject(Integer.toString(i), Integer.toString(j), Integer.toString(i * j));
                codeMatcher.put(testResultObject);
            }
        }
        watch.stop();
        logger.info("Test cache finished, cost: " + watch.getTime());
        // 检查查询 10000 次的效率
        watch.reset();
        watch.start();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
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
