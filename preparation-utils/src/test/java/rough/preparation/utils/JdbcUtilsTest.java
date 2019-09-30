package rough.preparation.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.TestCase;

public class JdbcUtilsTest extends TestCase {

    public void testCreateConnection() throws Exception {
        Connection conn = JdbcUtils.createConnection();
        assertNotNull(conn);
        StringBuffer sql = new StringBuffer();
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

        conn = JdbcUtils.createConnection("oracle.jdbc.driver.OracleDriver",
                "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = 10.32.66.17)(PORT = 1521)) (ADDRESS = (PROTOCOL = TCP)(HOST = 10.32.66.18)(PORT = 1521)) ) (LOAD_BALANCE = yes) (CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = shdw) ) )",
                "fin_look", "fin_look");
        sql = new StringBuffer();
        sql.append("select unistr('\\d845\\deb8') from dual");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql.toString());
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

}
