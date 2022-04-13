package com.hhb.sqlSession;

import com.hhb.pojo.Configuration;
import com.hhb.pojo.MappedStatement;

import java.util.List;

/**
 * @author hhb
 * @date 2022/4/13
 * @description
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMap().get(statementId);
        List<E> list = simpleExecutor.query(configuration, mappedStatement, params);
        return list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... param) {
        List<T> list = selectList(statementId, param);
        if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new RuntimeException();
        }
    }
}
