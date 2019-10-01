package com.test.JDBCTEST;

import java.sql.*;

public class JDBCTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet rs = null;
        try{
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取连接
            String url = "jdbc:mysql://localhost:3307/ssmdemo?serverTimezone=GMT%2B8";
            String user = "root";
            String password = "123456";
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);

//            connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from tb_user";//sql 不带参数的
            Statement stmt = conn.createStatement();    //创建一个statement对象
            rs = stmt.executeQuery(sql);        //执行查询
            int id, sex;
            String userName, name;
            System.out.println("id\t姓名\t性别\t");
            while(rs.next()) {        //遍历结果集
              id = rs.getInt("id");
              userName = rs.getString("userName");
              sex = rs.getInt("sex");
              name = rs.getString("name");
              System.out.println(id + "\t" + userName + "\t" + sex + "\t" + name);
                       }

            sql = "select * from tb_user where id=?";//sql带参数的
            prepareStatement = conn.prepareStatement(sql);
            // 设置参数
            prepareStatement.setLong(1, 1);//前面的1指的是第一个参数，后面的1是入参。select * from tb_user where id=1
            // 执行查询
            rs = prepareStatement.executeQuery();
            // 处理结果集
            while (rs.next()) {
                System.out.println(rs.getString("userName"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getInt("age"));
                System.out.println(rs.getDate("birthday"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {//关闭连接，释放资源
            if (rs != null) {
                rs.close();
            }
            if (prepareStatement != null) {
                prepareStatement.close();
            }
            if (connection != null) {
                connection.close();
            }

        }


    }

}


//jdbc缺点：结果集数据类型 需要手动判断；列名需要手动输入
//创建数据库表：
//DROP TABLE IF EXISTS tb_user;
//        CREATE TABLE tb_user (
//        id int(32) NOT NULL,
//        userName varchar(32) DEFAULT NULL,
//        password varchar(32) DEFAULT NULL,
//        name varchar(32) DEFAULT NULL,
//        age int(10) DEFAULT NULL,
//        sex int(2) DEFAULT NULL,
//        birthday date DEFAULT NULL,
//        created datetime DEFAULT NULL,
//        updated datetime DEFAULT NULL,
//        PRIMARY KEY (id)
//        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
//
//
//        INSERT INTO ssmdemo.tb_user ( userName, password, name, age, sex, birthday, created, updated) VALUES ( 'zpc', '123456', '鹏程', '22', '1', '1990-09-02', sysdate(), sysdate());
//        INSERT INTO ssmdemo.tb_user ( userName, password, name, age, sex, birthday, created, updated) VALUES ( 'hj', '123456', '静静', '22', '1', '1993-09-05', sysdate(), sysdate());
