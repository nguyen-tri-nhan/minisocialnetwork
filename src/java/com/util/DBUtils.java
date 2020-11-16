/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author nguyentrinhan2000
 */
public class DBUtils {

    public static Connection getConnection() throws ClassNotFoundException, SQLException, NamingException {
//        Connection conn = null;
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=LibraryManagement;";
//        conn = DriverManager.getConnection(url, "sa", "123456789");
//        return conn;
        Context ctex = new InitialContext();
        Context cnCtex = (Context) ctex.lookup("java:comp/env");
        DataSource src = (DataSource) cnCtex.lookup("LAB1");
        Connection conn = (Connection) src.getConnection();
        return conn;
    }
}
