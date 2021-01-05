package com.lemon.util;

import com.lemon.data.Constants;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JDBCUtils {

    /**
     * 获取到数据库连接对象
     *
     * @return
     */

    @Test
    public static void test(){
        getConnection();
    }

    public static Connection getConnection() {
        //定义数据库连接
        //Oracle：jdbc:oracle:thin:@localhost:1521:DBName
        //SqlServer：jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=DBName
        //MySql：jdbc:mysql://localhost:3306/DBName
        String url = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USER;
        String password = Constants.DATABASE_PWD;
        //定义数据库连接对象
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void update(String sql) {
        //1、获取到数据库连接对象
        Connection conn = getConnection();
        //2、数据库操作
        QueryRunner queryRunner = new QueryRunner();
        try {
            queryRunner.update(conn, sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //3、关闭数据库连接
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 查询所有的结果集
     *
     * @param sql 要执行的sql语句
     * @return
     */
    public static List<Map<String, Object>> queryAll(String sql) {
        //1、获取到数据库连接对象
        Connection conn = getConnection();
        //2、数据库操作
        QueryRunner queryRunner = new QueryRunner();
        try {
            //第一个参数：数据库连接对象 ，第二个参数：执行sql语句 第三个参数：接收查询结果
            List<Map<String, Object>> result = queryRunner.query(conn, sql, new MapListHandler());
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //3、关闭数据库连接
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 查询结果集中的第一条数据
     *
     * @param sql 要执行的sql语句
     * @return 结果
     */
    public static Map<String, Object> queryOne(String sql) {
        //1、获取到数据库连接对象
        Connection conn = getConnection();
        //2、数据库操作
        QueryRunner queryRunner = new QueryRunner();
        try {
            //第一个参数：数据库连接对象 ，第二个参数：执行sql语句 第三个参数：接收查询结果
            Map<String, Object> result = queryRunner.query(conn, sql, new MapHandler());
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //3、关闭数据库连接
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 查询结果集中的单个数据
     *
     * @param sql 要执行的sql语句
     * @return 结果
     */
    public static Object querySingle(String sql) {
        //1、获取到数据库连接对象
        Connection conn = getConnection();
        //2、数据库操作
        QueryRunner queryRunner = new QueryRunner();
        try {
            //第一个参数：数据库连接对象 ，第二个参数：执行sql语句 第三个参数：接收查询结果
            Object result = queryRunner.query(conn, sql, new ScalarHandler<Object>());
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //3、关闭数据库连接
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}