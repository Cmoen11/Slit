/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth;
import java.sql.*;
/**
 *
 * @author Christian
 */
public class Database{
    protected static final String url = "jdbc:mysql://62.122.255.10:3306/",
            dbName = "is-100",
            driver = "com.mysql.jdbc.Driver",
            userName = "server",
            password = "ChristianErKul";
    
    protected ResultSet res;
    protected Statement st;
    protected static String sql;
    
    /**
     * To get information from the database.
     * @param sql
     * @throws Exception 
     */
    public ResultSet executeQuery(String sql) throws Exception {
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url + dbName, userName, password);
        st = conn.createStatement();
        ResultSet res = st.executeQuery(sql);
        return res;
    }
    /**
     * Add a update to the database.
     * 
     * @param sql               The query
     * @throws Exception        The exception
     */
    
    public void executeUpdate(String sql) throws Exception{
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url + dbName, userName, password);
        Statement st = conn.createStatement();
        st.executeUpdate(sql);
    }
}
