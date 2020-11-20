package rough.preparation.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class JdbcUtils {
    private static final Log logger = LogFactory.getLog(JdbcUtils.class);

    public static final String JDBC_DRIVER = UtilsConfig.getString("JdbcUtils.JDBC_DRIVER");
    public static final String JDBC_URL = UtilsConfig.getString("JdbcUtils.JDBC_URL");
    public static final String JDBC_USER_NAME = UtilsConfig
            .getString("JdbcUtils.JDBC_USER_NAME");
    public static final String JDBC_PASSWORD = UtilsConfig.getString("JdbcUtils.JDBC_PASSWORD");

    public static Connection createConnection() throws Exception {
        return createConnection(JDBC_DRIVER, JDBC_URL, JDBC_USER_NAME, JDBC_PASSWORD);
    }

    public static Connection createConnection(String driver, String url, String userName,
            String password) throws Exception {
        Class.forName(driver);

        return DriverManager.getConnection(url, userName, password);
    }

    public static void closeResultSetSilently(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error(e, e);
            }
        }
    }

    public static void closeStatementSilently(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.error(e, e);
            }
        }
    }

    public static void closePreparedStatementSilently(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                logger.error(e, e);
            }
        }
    }
    
    public static void closeConnectionSilently(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(e, e);
            }
        }
    }
}
