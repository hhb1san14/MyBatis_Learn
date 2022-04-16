package com.hhb.config;

import com.hhb.io.Resource;
import com.hhb.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author hhb
 * @date 2022/4/12
 * @description
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        configuration = new Configuration();
    }

    /**
     * 使用Dom4J将输入流转化成Configuration对象
     *
     * @param is
     * @return
     */
    public Configuration parseConfig(InputStream is) {
        try {
            // 读取流信息
            Document document = new SAXReader().read(is);
            //获取根节点信息
            Element rootElement = document.getRootElement();
            // 封装Configuration中的DataSource
            parseSqlMapperConfig(rootElement);
            // 获取配置文件中的 mapper节点的路径信息并专程inputStream
            List<Element> mapperElementList = rootElement.selectNodes("//mapper");
            for (Element element : mapperElementList) {
                // 获取到Mapper的路径信息后，将该文件转化成输入流，并进行构建成对象
                new XMLMapperBuilder(configuration).parse(Resource.getResourceAsInputStream(element.attributeValue("name")));
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return configuration;
    }

    /**
     * 传入根节点信息，解析后封装到Configuration中的DataSource中
     *
     * @param rootElement
     */
    private void parseSqlMapperConfig(Element rootElement) {
        try {
            // 获取跟节点下所有带有properties节点的元素
            List<Element> elementList = rootElement.selectNodes("//properties");
            // 將所有的数据库配置信息存放到Properties里面
            Properties properties = new Properties();
            for (Element element : elementList) {
                // properties 里面的name
                String name = element.attributeValue("name");
                // properties 里面的value
                String value = element.attributeValue("value");
                properties.setProperty(name, value);
            }
            // 创建c3p0对象
            ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
            comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
            comboPooledDataSource.setUser(properties.getProperty("username"));
            comboPooledDataSource.setPassword(properties.getProperty("password"));
            // 封装成Configuration
            configuration.setDataSource(comboPooledDataSource);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}
