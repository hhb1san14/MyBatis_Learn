package com.hhb;

import com.hhb.dao.UserMapper;
import com.hhb.io.Resource;
import com.hhb.pojo.User;
import com.hhb.sqlSession.SqlSession;
import com.hhb.sqlSession.SqlSessionFactory;
import com.hhb.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author hhb
 * @date 2022/4/13
 * @description
 */
public class IPersistenceTest {


    @Test
    public void test() {
        InputStream resourceAsInputStream = Resource.getResourceAsInputStream("sqlMapperConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsInputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();


        // 代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = mapper.queryAll();
        System.err.println(list);
        User user = new User();
        user.setId(1L);
        User result = mapper.selectById(user);
        System.err.println(result);
    }


}
