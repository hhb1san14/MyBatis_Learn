package com.hhb.sqlSession;

import com.hhb.pojo.Configuration;
import com.hhb.pojo.MappedStatement;

import java.util.List;

/**
 * @author hhb
 * @date 2022/4/13
 * @description
 */
public interface Executor {

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object ... params);
}
