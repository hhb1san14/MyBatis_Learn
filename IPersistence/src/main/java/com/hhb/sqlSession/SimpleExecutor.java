package com.hhb.sqlSession;

import com.hhb.pojo.BoundSql;
import com.hhb.pojo.Configuration;
import com.hhb.pojo.MappedStatement;
import com.hhb.util.GenericTokenParser;
import com.hhb.util.ParameterMapping;
import com.hhb.util.ParameterMappingTokenHandler;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author hhb
 * @date 2022/4/13
 * @description
 */
public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) {

        try {
            //1、注册驱动，创建数据库链接
            Connection connection = configuration.getDataSource().getConnection();
            // 获取SQL语句：select * from user where id = #{id} and userName = #{userName}
            // 转化SQL语句：select * from user where id = ? and userName = ?
            String sql = mappedStatement.getSql();
            BoundSql boundSql = getBoundSql(sql);
            // 3、获取prepareStatement select * from user where id = ? and userName = ?
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getParseSql());
            // 4 、 设置参数
            //获取参数的对象的全路径
            String paramType = mappedStatement.getParamType();
            Class<?> clazz = getClassType(paramType);
            //获取类里面有的属性，并通过反射将值获取到
            List<ParameterMapping> paramsList = boundSql.getParamsList();
            for (int i = 0; i < paramsList.size(); i++) {
                ParameterMapping parameterMapping = paramsList.get(i);
                // content 就是当时SQL里面的 id name
                String content = parameterMapping.getContent();
                // 获取字段
                Field field = clazz.getDeclaredField(content);
                // 防止属性私有，暴力破解
                field.setAccessible(true);
                // 假设可变参数只有一个
                Object o = field.get(params[0]);
                preparedStatement.setObject(i + 1, o);
            }
            // 5、 执行SQL

            // 6、封装返回的结果、

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class<?> getClassType(String paramType) throws ClassNotFoundException {
        if (paramType != null && !"".equals(paramType)) {
            Class<?> aClass = Class.forName(paramType);
            return aClass;
        }
        return null;
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        // 转化好的SQL
        String parseSQL = genericTokenParser.parse(sql);
        //转化好的SQL中的#{}中的值的集合
        List<ParameterMapping> paramsList = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parseSQL, paramsList);
    }
}
