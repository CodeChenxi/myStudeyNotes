package com.atguigu.api.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @projectName: linchenxi-jdbc
 * @package: com.atguigu.api.utils
 * @className: JdbcUtils
 * @author: 林晨曦
 * @description:
 *  v1.0版本工具类
 *      内部包含一个连接池对象，并且对外提供获取连接和回收连接的方法！
 *
 *  小建议：
 *      工具类的方法，推荐写成静态，外部调用会更加方便！
 *
 *  实现：
 *      属性 连接池对象【实例化一次】
 *          单例模式
 *          static{
 *
 *                 全局调用一次
 *          }
 *      方法
 *          对外提供连接的方法
 *          回收外部传入连接方法
 * @date: 2023/1/13 11:50
 * @version: 1.0
 */

public class JdbcUtils {

    // 连接池对象
    private static DataSource dataSource = null;

    static{

    // 初始化连接池对象

        Properties properties = new Properties();
        InputStream ips = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(ips);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param :
     * @return Connection
     * 对外提供连接的方法
     */

    public static Connection getConnection() throws SQLException {

        return dataSource.getConnection();
    }


    public static void freeConnection(Connection connection) throws SQLException {

        connection.close();//连接池的连接，调用close就是释放
    }

}
