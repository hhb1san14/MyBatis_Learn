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


    private SqlSessionFactory config(InputStream is) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(is);

        return null;
    }

}
