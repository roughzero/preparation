package rough.preparation.utils;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class TablePartitionQueryTest {
    protected static Log logger = LogFactory.getLog(TablePartitionQueryTest.class);
    private static final Random random = new Random();
    private static final int TOTAL = 1000000000;
    private static final NumberFormat format = new DecimalFormat("AEN00000000000000000");
    public static final String SQL_QUERY = "SELECT MSTRING09 FROM PT_AD_ACC_ENTRIE WHERE SERI_NO = ?";

    public static String queryByRandom(PreparedStatement preparedStatement) throws SQLException {
        int id = random.nextInt(TOTAL);
        String pk = format.format(id);
        preparedStatement.setString(1, pk);
        String result = null;
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            result = rs.getString(1);
        }
        rs.close();
        return result;
    }


    public static long queryTest(PreparedStatement preparedStatement, int times) throws SQLException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < times; i++) {
            queryByRandom(preparedStatement);
        }
        stopWatch.stop();
        return stopWatch.getTime();
    }

    public static void main(String[] args) throws Exception {
        Connection connection = JdbcUtils.createConnection("oracle.jdbc.driver.OracleDriver",
                "jdbc:oracle:thin:@192.168.5.128:1521:demo",
                "rough", "rough");
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
        TablePartitionQueryTest.queryByRandom(preparedStatement); // 忽略第一次查询
        int rounds = 10;
        for (int i = 0; i < rounds; i++) {
            long cost = queryTest(preparedStatement, 1000);
            logger.info("Query round: " + i + " finished, cost: " + cost);
        }
        preparedStatement.close();
        connection.close();
    }
}
