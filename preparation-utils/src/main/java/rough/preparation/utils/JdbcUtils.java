package rough.preparation.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public final class JdbcUtils {
    public static final String JDBC_DRIVER = UtilsConfig.getString("JdbcUtils.JDBC_DRIVER");
    public static final String JDBC_URL = UtilsConfig.getString("JdbcUtils.JDBC_URL");
    public static final String JDBC_USER_NAME = UtilsConfig.getString("JdbcUtils.JDBC_USER_NAME");
    public static final String JDBC_PASSWORD = UtilsConfig.getString("JdbcUtils.JDBC_PASSWORD");

    public static Connection createConnection() throws Exception {
        return createConnection(JDBC_DRIVER, JDBC_URL, JDBC_USER_NAME, JDBC_PASSWORD);
    }

    public static Connection createConnection(String driver, String url, String userName, String password) throws Exception {
        Class.forName(driver);

        Connection result = DriverManager.getConnection(url, userName, password);

        return result;
    }
}
