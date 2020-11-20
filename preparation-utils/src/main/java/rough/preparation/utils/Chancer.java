/*
 * Created on 2012-1-29
 */
package rough.preparation.utils;

import java.util.Random;

public class Chancer {
    private final Random random;
    private final int chance;
    private final int total;

    public Chancer(int chance, int total) {
        this.chance = chance;
        this.total = total;
        this.random = new Random();
    }

    public boolean wouldBe() {
        int symbol = random.nextInt(total);
        return symbol < chance;
    }
}
