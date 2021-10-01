package rough.preparation.utils.batch;

import lombok.extern.apachecommons.CommonsLog;
import org.junit.Assert;
import org.junit.Test;
import rough.preparation.batch.ListTaskProvider;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
public class ListTaskProviderTest {

    @Test
    public void testNextTask() {
        List<Integer> tasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            tasks.add(i);
        }
        ListTaskProvider<Integer> listTaskProvider = new ListTaskProvider<>(tasks);
        int taskSize = 0;
        while (true) {
            Integer task = listTaskProvider.nextTask();
            if (task == null) {
                break;
            } else {
                taskSize++;
            }
        }
        Assert.assertEquals(1000, taskSize);
    }
}
