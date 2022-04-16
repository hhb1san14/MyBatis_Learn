package com.hhb.sqlSession;

import com.hhb.pojo.BoundSql;
import com.hhb.pojo.Configuration;
import com.hhb.pojo.MappedStatement;
import com.hhb.util.GenericTokenParser;
import com.hhb.util.ParameterMapping;
import com.hhb.util.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hhb
 * @date 2022/4/13
 * @description
 */
public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) {
        List<Object> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1、注册驱动，创建数据库链接
            connection = configuration.getDataSource().getConnection();
            // 获取SQL语句：select * from user where id = #{id} and userName = #{userName}
            // 转化SQL语句：select * from user where id = ? and userName = ?
            String sql = mappedStatement.getSql();
            BoundSql boundSql = getBoundSql(sql);
            // 3、获取prepareStatement select * from user where id = ? and userName = ?
            preparedStatement = connection.prepareStatement(boundSql.getParseSql());
            // 4 、 设置参数
            setParam(preparedStatement, mappedStatement.getParamType(), boundSql.getParamsList(), params);
            // 5、 执行SQL
            resultSet = preparedStatement.executeQuery();
            // 6、封装返回的结果、
            getResult(list, resultSet, mappedStatement.getResultType());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (List<E>) list;
    }


    /**
     * 设置参数
     *
     * @param preparedStatement
     * @param paramType         获取参数的对象的全路径
     * @param paramsList        获取类里面有的属性，并通过反射将值获取到
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws SQLException
     */
    private void setParam(PreparedStatement preparedStatement, String paramType, List<ParameterMapping> paramsList, Object... params) throws ClassNotFoundException, NoSuchFieldException, SQLException, IllegalAccessException {
        Class<?> clazz = getClassType(paramType);

        for (int i = 0; i < paramsList.size(); i++) {
            ParameterMapping parameterMapping = paramsList.get(i);
            // content 就是当时SQL里面的 id name
            String content = parameterMapping.getContent();
            // 获取字段
            Field field = clazz.getDeclaredField(content);
            // 防止属性私有，暴力破解
            field.setAccessible(true);
            //假设只有一个可变参数且为对象
            Object o = field.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }
    }

    /**
     * 封装结果
     *
     * @param resultSet  数据库结果集
     * @param resultType 返回值类型
     * @param list       返回值
     * @return
     */
    private void getResult(List<Object> list, ResultSet resultSet, String resultType) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        while (resultSet.next()) {
            Class<?> aClass = getClassType(resultType);
            // 创建对象
            Object o = aClass.newInstance();
            // 获取元数据信息
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                // 获取字段名
                String columnName = metaData.getColumnName(i + 1);
                // 获取对应字段的值
                Object value = resultSet.getObject(columnName);
                //使用反射或者内省，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, aClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
            list.add(o);
        }
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
