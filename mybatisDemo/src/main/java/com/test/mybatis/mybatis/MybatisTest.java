package com.test.mybatis.mybatis;

import com.test.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {
    public static void main(String[] args) throws IOException {
        //指定全局配置文件
        String resource="mybatis-config.xml";
        //读取配置文件
        InputStream inputStream= Resources.getResourceAsStream(resource);

        //构建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {//可能抛出异常

            //操作CRUD（增删改查），第一个参数：指定statement，规则：命名空间+“.”+statementId
            //第二个参数：指定传入sql的参数：这里是用户id
            //User 类实例没有使用import导入，属于第一种情况
//            第一：如果两个代码都在同一个包中的话， 是不用使用import语句来调用的。 可以直接使用另一个主类，通过新建对象的方式使用类的方法等等。
//            第二：如果两个代码不在同一个包中，可以使用"import 包名.类名"的形式来导入另一个主类，然后还是通过新建对象的方式使用主类的方法等。
            User user=sqlSession.selectOne("MyMapper.selectUser",1);
            System.out.println(user);
            System.out.println(user.getAge());//lombok包，Data注解，自带get，set方法
        }
        finally {//不管有无异常都执行
            System.out.println("不管有没有异常都执行");
            sqlSession.close();
        }



    }
}
