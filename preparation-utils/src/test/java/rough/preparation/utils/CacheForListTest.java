package rough.preparation.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CacheForListTest {
    static class ResultTestCacheForListGender extends AbstractCacheForList<RuleTestDTO> {
        @Override
        protected boolean isUsingChildCache() {
            return true;
        }

        @Override
        protected CacheForList<RuleTestDTO> childCache() {
            return new ResultTestCacheForListLevel();
        }

        @Override
        protected Object getKeyByBusiObject(RuleTestDTO value) {
            return value.getGender();
        }
    }

    static class ResultTestCacheForListLevel extends AbstractCacheForList<RuleTestDTO> {

        @Override
        protected boolean isUsingChildCache() {
            return false;
        }

        @Override
        protected CacheForList<RuleTestDTO> childCache() {
            return null;
        }

        @Override
        protected Object getKeyByBusiObject(RuleTestDTO value) {
            return value.getLevel();
        }
    }

    @Test
    public void testCache() {
        List<RuleTestDTO> ruleTestDTOS = new ArrayList<>();
        ruleTestDTOS.add(new RuleTestDTO("F", 1, "D", 10));
        ruleTestDTOS.add(new RuleTestDTO("F", 1, "C", 20));
        ruleTestDTOS.add(new RuleTestDTO("F", 1, "B", 30));
        ruleTestDTOS.add(new RuleTestDTO("F", 1, "A", 40));
        ruleTestDTOS.add(new RuleTestDTO("F", 1, "S", 100));
        ruleTestDTOS.add(new RuleTestDTO("F", 2, "D", 15));
        ruleTestDTOS.add(new RuleTestDTO("F", 2, "C", 30));
        ruleTestDTOS.add(new RuleTestDTO("F", 2, "B", 45));
        ruleTestDTOS.add(new RuleTestDTO("F", 2, "A", 60));
        ruleTestDTOS.add(new RuleTestDTO("F", 2, "S", 120));
        ruleTestDTOS.add(new RuleTestDTO("M", 1, "D", 12));
        ruleTestDTOS.add(new RuleTestDTO("M", 1, "C", 24));
        ruleTestDTOS.add(new RuleTestDTO("M", 1, "B", 36));
        ruleTestDTOS.add(new RuleTestDTO("M", 1, "A", 48));
        ruleTestDTOS.add(new RuleTestDTO("M", 1, "S", 108));
        ruleTestDTOS.add(new RuleTestDTO("M", 2, "D", 20));
        ruleTestDTOS.add(new RuleTestDTO("M", 2, "C", 40));
        ruleTestDTOS.add(new RuleTestDTO("M", 2, "B", 60));
        ruleTestDTOS.add(new RuleTestDTO("M", 2, "A", 80));
        ruleTestDTOS.add(new RuleTestDTO("M", 2, "S", 150));

        CacheForList<RuleTestDTO> cache = new ResultTestCacheForListGender();
        cache.put(ruleTestDTOS);

        RuleTestDTO ruleTestDTO = new RuleTestDTO("F", 1, "A", 0);

        List<RuleTestDTO> results = cache.get(ruleTestDTO);

        Assert.assertNotNull(results);
        Assert.assertEquals(2, results.size());
        results.sort(Comparator.comparing(RuleTestDTO::getValue));
        Assert.assertEquals(40, results.get(0).getValue());
        Assert.assertEquals(60, results.get(1).getValue());

        ruleTestDTO = new RuleTestDTO("M", 1, "S", 0);
        results = cache.get(ruleTestDTO);

        Assert.assertNotNull(results);
        Assert.assertEquals(2, results.size());
        results.sort(Comparator.comparing(RuleTestDTO::getValue));
        Assert.assertEquals(108, results.get(0).getValue());
        Assert.assertEquals(150, results.get(1).getValue());
    }
}
