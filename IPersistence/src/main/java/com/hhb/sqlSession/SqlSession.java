package com.hhb.sqlSession;

import java.util.List;

/**
 * @author hhb
 * @date 2022/4/13
 * @description
 */
public interface SqlSession {

    <E> List<E> selectList(String statementId, Object... params);

    <T> T selectOne(String statementId, Object... params);

    /**
     *
     * @param mapperClass
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<?> mapperClass);
}
