package rough.preparation.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.TestCase;

public class JdbcUtilsTest extends TestCase {

    public void testCreateConnection() throws Exception {
        Connection conn = JdbcUtils.createConnection();
        assertNotNull(conn);
        StringBuilder sql;
        sql = new StringBuilder();
        sql.append(" CREATE TABLE BUSINESS_LOCK");
        sql.append(" (");
        sql.append(" LOCK_TYPE VARCHAR(20) NOT NULL,");
        sql.append(" LOCK_KEY VARCHAR(40) NOT NULL,");
        sql.append(" LOCKER VARCHAR(20),");
        sql.append(" LOCK_TIME TIMESTAMP,");
        sql.append(" PRIMARY KEY(LOCK_TYPE, LOCK_KEY)");
        sql.append(" )");
        Statement st = conn.createStatement();
        st.execute(sql.toString());
    }

}
