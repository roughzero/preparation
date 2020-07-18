package rough.preparation.utils;

public class YearMatcher extends AbstractObjectMatcher<TestQueryObject, TestResultObject> implements ObjectMatcher<TestQueryObject, TestResultObject> {

    public YearMatcher() {
        super(null);
    }

    public YearMatcher(Class<ObjectMatcher<TestQueryObject, TestResultObject>> childMatcherPrototype) {
        super(childMatcherPrototype);
    }

    @Override
    protected Object getKeyByResult(TestResultObject testResultObject) {
        return testResultObject == null ? null : testResultObject.getYear();
    }

    @Override
    protected Object getKeyBuQuery(TestQueryObject testQueryObject) {
        return testQueryObject == null ? null : testQueryObject.getYear();
    }
}
