package com.hhb.config;

import com.hhb.pojo.Configuration;
import com.hhb.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author hhb
 * @date 2022/4/12
 * @description
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析SqlMapper信息
     *
     * @param inputStream
     */
    public void parse(InputStream inputStream) {
        try {
            // 将流信息转化成Document对象，并获取根节点信息
            Element rootElement = new SAXReader().read(inputStream).getRootElement();
            //获取根节点下所有的select节点信息，
            List<Element> selectElementList = rootElement.selectNodes("//select");
            //获取跟节点下的namespace信息
            String namespace = rootElement.attributeValue("namespace");
            // 解析所有的select节点
            for (Element element : selectElementList) {
                String id = element.attributeValue("id");
                String resultType = element.attributeValue("resultType");
                String paramType = element.attributeValue("paramType");
                String sql = element.getTextTrim();
                MappedStatement mappedStatement = new MappedStatement(id, resultType, paramType, sql);
                String key = namespace + "." + id;
                // 设置configuration中对象属性
                configuration.getMap().put(key, mappedStatement);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
