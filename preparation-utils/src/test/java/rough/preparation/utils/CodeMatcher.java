package rough.preparation.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings({"rawtypes", "unused"})
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
}
