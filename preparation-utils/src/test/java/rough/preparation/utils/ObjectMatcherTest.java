package rough.preparation.utils;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ObjectMatcherTest {
    private static final int TEST_COUNT = 1000;
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
                Assert.assertNotNull(resultObject);
                Assert.assertEquals(Integer.toString(i * j), resultObject.getRate());
            }
        }
        watch.stop();
        logger.info("Test query finished, cost: " + watch.getTime());

        codeMatcher = null;
        List<TestResultObject> resultObjects = new ArrayList<>();
        watch.reset();
        watch.start();
        for (int i = 0; i < TEST_COUNT; i++) {
            for (int j = 0; j < TEST_COUNT; j++) {
                TestResultObject testResultObject = new TestResultObject(Integer.toString(i), Integer.toString(j), Integer.toString(i * j));
                resultObjects.add(testResultObject);
            }
        }
        watch.stop();
        logger.info("List cache finished, cost: " + watch.getTime());

        watch.reset();
        watch.start();
        for (int i = 0; i < TEST_COUNT; i++) {
            for (int j = 0; j < TEST_COUNT; j++) {
                TestQueryObject queryObject = new TestQueryObject(i + "," + j, Integer.toString(i), Integer.toString(j));
                TestResultObject resultObject = match(queryObject, resultObjects);
                Assert.assertNotNull(resultObject);
                Assert.assertEquals(Integer.toString(i * j), resultObject.getRate());
            }
        }
        watch.stop();
        logger.info("Test query by List finished, cost: " + watch.getTime());
    }

    public static TestResultObject match(TestQueryObject queryObject, List<TestResultObject> resultObjectList) {
        if (queryObject == null) {
            return null;
        } else {
            for (TestResultObject resultObject : resultObjectList) {
                if ((queryObject.getCode() == null && resultObject.getCode() == null ||
                        queryObject.getCode().equals(resultObject.getCode())) &&
                        (queryObject.getYear() == null && resultObject.getYear() == null ||
                                queryObject.getYear().equals(resultObject.getYear()))
                ) {
                    return resultObject;
                }
            }
        }
        return null;
    }
}
