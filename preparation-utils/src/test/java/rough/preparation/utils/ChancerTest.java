/*
 * Created on 2012-1-30
 */
package rough.preparation.utils;

import junit.framework.TestCase;

public class ChancerTest extends TestCase {

    public void testWouldBe() {
        int testCount = 10000;
        Chancer chancer = new Chancer(2, 3);
        int trues = 0, falses = 0;
        for (int i = 0; i < testCount; i++) {
            if (chancer.wouldBe())
                trues++;
            else
                falses++;
        }
        assertTrue(trues >= testCount * 2 / 3 * 0.9);
        assertTrue(trues <= testCount * 2 / 3 * 1.1);
        assertTrue(falses >= testCount * 1 / 3 * 0.9);
        assertTrue(falses <= testCount * 1 / 3 * 1.1);
    }

}
