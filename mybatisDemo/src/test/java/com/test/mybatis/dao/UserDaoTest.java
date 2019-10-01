package com.test.mybatis.dao;

import com.test.mybatis.dao.impl.UserDaoImpl;
import com.test.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;

public class UserDaoTest {

    public UserDao userDao;//定义接口类的对象
    public UserDaoImpl userDaoImpl;//定义实现类的对象
    public SqlSession sqlSession;

    @BeforeMethod
    public void setUp() throws IOException {
        // mybatis-config.xml
        String resource = "mybatis-config.xml";
        // 读取配置文件
        InputStream is = Resources.getResourceAsStream(resource);
        // 构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = sqlSessionFactory.openSession();
        // 获取sqlSession
//        this.userDao = new UserDaoImpl(sqlSession);//初始化sqlsession，调用的是userDaoImpl类
        this.userDaoImpl=new UserDaoImpl(sqlSession);//初始化sqlsession，调用的是userDaoImpl类。和上面的语句作用相同
    }

    @Test
    public void testQueryUserById() {
//        System.out.println(this.userDao.queryUserById("1"));//调用UserDaoImpl类中的方法：queryUserById
        System.out.println(this.userDaoImpl.queryUserById("1"));
    }

    @Test
    public void testQueryUserAll() {
        List<User> userList=this.userDaoImpl.queryUserAll();//List<User>意识就是说，现在你所声明的List只能存放User对象了
//        System.out.println(userList instanceof Object);//判断是什么类型
        System.out.println(userList instanceof List);//判断是什么类型,是List类型，返回true

        for (User user:userList//遍历list
             ) {
            System.out.println(user);
        }
    }

    @Test
    public void testInsertUser() {
        User user=new User();
        user.setAge(16);//由于引入了lombok的Data标签，自带set等方法
        user.setBirthday(new Date("1990/09/02"));
        user.setName("大鹏");
        user.setPassword("123456");
        user.setSex(1);
        user.setUserName("evan");
        this.userDaoImpl.insertUser(user);
        this.sqlSession.commit();
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setBirthday(new Date());
        user.setName("静鹏");
        user.setPassword("654321");
        user.setSex(1);
        user.setUserName("evanjin");
        user.setId("1");
        this.userDaoImpl.updateUser(user);
        this.sqlSession.commit();
    }

    @Test
    public void testDeleteUser() {
        this.userDaoImpl.deleteUser("4");
        this.sqlSession.commit();
    }
}