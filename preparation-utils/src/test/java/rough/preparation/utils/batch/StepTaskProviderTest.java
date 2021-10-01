package rough.preparation.utils.batch;

import org.junit.Assert;
import org.junit.Test;
import rough.preparation.batch.StepTaskProvider;

public class StepTaskProviderTest {
    @Test
    public void test() {
        StepTaskProvider stepTaskProvider = new StepTaskProvider();
        Assert.assertEquals(100, stepTaskProvider.getTasks());
        Assert.assertEquals(1, stepTaskProvider.getStart());
        Assert.assertEquals(1, stepTaskProvider.getStep());
    }
}
