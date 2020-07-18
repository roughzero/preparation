package rough.preparation.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CodeMatcher extends AbstractObjectMatcher<TestQueryObject, TestResultObject> {
    protected static Log logger = LogFactory.getLog(CodeMatcher.class);

    public CodeMatcher(Class childMatcherPrototype) {
        super(childMatcherPrototype);
    }

    @Override
    protected Object getKeyByResult(TestResultObject testResultObject) {
        return testResultObject == null ? null : testResultObject.getCode();
    }

    @Override
    protected Object getKeyBuQuery(TestQueryObject testQueryObject) {
        return testQueryObject == null ? null : testQueryObject.getCode();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        CodeMatcher codeMatcher = new CodeMatcher(YearMatcher.class);
        TestQueryObject queryObject01 = new TestQueryObject();
        queryObject01.setCode("1");
        queryObject01.setName("Test01");
        queryObject01.setYear("1");

        TestResultObject resultObject01 = new TestResultObject("1", "1", "1");
        TestResultObject resultObject02 = new TestResultObject("1", "2", "2");
        TestResultObject resultObject03 = new TestResultObject("2", "1", "3");
        TestResultObject resultObject04 = new TestResultObject("2", "2", "4");

        codeMatcher.put(resultObject01);
        codeMatcher.put(resultObject02);
        codeMatcher.put(resultObject03);
        codeMatcher.put(resultObject04);

        logger.info(codeMatcher.get(queryObject01).getRate());
        queryObject01.setYear("2");
        logger.info(codeMatcher.get(queryObject01).getRate());
        queryObject01.setCode("2");
        logger.info(codeMatcher.get(queryObject01).getRate());
        queryObject01.setYear("1");
        logger.info(codeMatcher.get(queryObject01).getRate());
    }
}
