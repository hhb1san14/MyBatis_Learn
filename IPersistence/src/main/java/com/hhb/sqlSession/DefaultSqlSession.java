package com.hhb.sqlSession;

import com.hhb.pojo.Configuration;
import com.hhb.pojo.MappedStatement;

import java.lang.reflect.*;
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

    /**
     * 返回的就是mapperClass接口的代理对象
     * 当代理对象调用接口的任意方法时，都会调用invoke方法
     *
     * @param mapperClass
     * @param <T>
     * @return
     */
    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 返回接口的代理对象
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            /**
             *
             * @param proxy 接口的代理对象
             * @param method 调用的接口的方法
             * @param args 传递的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层都是调用JDBC方法
                // 拼装StatementId 接口的全路径+方法名
                String name = method.getName();
                String nameClassUrl = method.getDeclaringClass().getName();
                String statementId = nameClassUrl + "." + name;
                // 获取方法的返回值，通过返回值判断调用list方法还是One的方法
                Type genericReturnType = method.getGenericReturnType();
                // 判断genericReturnType 是否进行进行了泛型类型参数化，就是有没有泛型List<T>
                if (genericReturnType instanceof ParameterizedType) {
                    return selectList(statementId, args);
                }
                return selectOne(statementId, args);
            }
        });

        return (T) proxyInstance;
    }
}
