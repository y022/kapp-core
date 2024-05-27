package com.kapp.database.jdbc;

import org.postgresql.Driver;

import java.sql.*;

public class JdbcV1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String url = "jdbc:postgresql://140.210.220.31:5432/postgres";

        String user = "postgres";

        String pd = "y1039390833";

        //注册驱动
        DriverManager.registerDriver(new Driver());

        //获取数据库连接
        Connection connection = DriverManager.getConnection(url, user, pd);

        String sql = "select * from table_control_valve";

        //通过连接执行sql
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();


        while (resultSet.next()) {
            System.out.println(resultSet.getString("id"));
        }

    }
}
