package com.hhb;

import com.hhb.io.Resource;
import com.hhb.sqlSession.SqlSession;
import com.hhb.sqlSession.SqlSessionFactory;
import com.hhb.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @author hhb
 * @date 2022/4/13
 * @description
 */
public class IPersistenceTest {


    public void test(){
        InputStream resourceAsInputStream = Resource.getResourceAsInputStream("sqlMapperConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsInputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

    }


}
