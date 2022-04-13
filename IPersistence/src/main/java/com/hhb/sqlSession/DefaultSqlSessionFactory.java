package com.hhb.sqlSession;

import com.hhb.pojo.Configuration;

/**
 * @author hhb
 * @date 2022/4/13
 * @description
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
