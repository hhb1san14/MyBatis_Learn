package com.hhb.sqlSession;

import com.hhb.config.XMLConfigBuilder;
import com.hhb.pojo.Configuration;

import java.io.InputStream;

/**
 * @author hhb
 * @date 2022/4/12
 * @description
 */
public class SqlSessionFactoryBuilder {


    public SqlSessionFactory build(InputStream is) {
        // 使用Dom4J解析配置文件，将解析出来的内容封装到Configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(is);
        // 创建SqlSessionFactory，工厂类，生产SqlSession对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }

}
