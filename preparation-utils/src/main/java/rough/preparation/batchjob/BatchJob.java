/*
 * Created on 2019年9月15日
 */
package rough.preparation.batchjob;

import java.math.BigDecimal;

public class BatchJob {
    private String batchParams;
    private int threads;
    private BigDecimal maxInvokes;

    public String getBatchParams() {
        return batchParams;
    }

    public void setBatchParams(String batchParams) {
        this.batchParams = batchParams;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public BigDecimal getMaxInvokes() {
        return maxInvokes;
    }

    public void setMaxInvokes(BigDecimal maxInvokes) {
        this.maxInvokes = maxInvokes;
    }
}
