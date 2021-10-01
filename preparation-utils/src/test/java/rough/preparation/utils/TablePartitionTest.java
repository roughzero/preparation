package rough.preparation.utils;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TablePartitionTest {
    protected static Log logger = LogFactory.getLog(TablePartitionTest.class);
    public static final String SQL_INSERT = "INSERT /*+APPEND+*/ INTO PT_AD_ACC_ENTRIE NOLOGGING SELECT ACC_NO, 'AEN' || TRIM ( TO_CHAR (TO_NUMBER (SERI_NO) + :THE_TIMES, '00000000000000000')), BATCH_NO, TRADE_SERI, LINE_ITEM_SERI, ENTRIE_NO, SSTRING01, SSTRING02, SSTRING03, SSTRING04, SSTRING05, SSTRING06, SSTRING07, SSTRING08, SSTRING09, SSTRING10, SSTRING11, SSTRING12, SSTRING13, SSTRING14, SSTRING15, SSTRING16, SSTRING17, SSTRING18, SSTRING19, SSTRING20, MSTRING01, MSTRING02, MSTRING03, MSTRING04, MSTRING05, MSTRING06, MSTRING07, MSTRING08, MSTRING09, MSTRING10, MSTRING11, MSTRING12, MSTRING13, MSTRING14, MSTRING15, MSTRING16, MSTRING17, MSTRING18, MSTRING19, MSTRING20, LSTRING01, LSTRING02, LSTRING03, LSTRING04, LSTRING05, LSTRING06, LSTRING07, LSTRING08, LSTRING09, LSTRING10, INT01, INT02, INT03, INT04, INT05, DECIMAL01, DECIMAL02, DECIMAL03, DECIMAL04, DECIMAL05, DATE01, DATE02, DATE03, DATE04, DATE05, DATE06, DATE07, DATE08, DATE09, DATE10 FROM PT_AD_ACC_ENTRIE_BAK";

    public static void main(String[] args) throws Exception {
        Connection connection = JdbcUtils.createConnection("oracle.jdbc.driver.OracleDriver",
                "jdbc:oracle:thin:@192.168.5.128:1521:demo",
                "rough", "rough");
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        int times = 1000;
        for (int i = 0; i < times; i++) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            preparedStatement.setInt(1, i * 1000000);
            preparedStatement.executeUpdate();
            connection.commit();
            stopWatch.stop();
            logger.info("Insert round: " + i + " finished, cost: " + stopWatch.getTime());
        }
        preparedStatement.close();
        connection.close();
    }
}
