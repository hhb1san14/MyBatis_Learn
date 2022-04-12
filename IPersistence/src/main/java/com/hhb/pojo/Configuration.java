package com.hhb.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hhb
 * @date 2022/4/12
 * @description 封装好数据库配置信息已经SQL信息，这样就进行参数传递时可以只传递一个参数
 */
public class Configuration {

    //封装的数据库配置信息
    private DataSource dataSource;

    // 封装的所有的SQL信息，key 就是 StatementID，以NameSpace+id组成
    private Map<String, MappedStatement> map = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMap() {
        return map;
    }

    public void setMap(Map<String, MappedStatement> map) {
        this.map = map;
    }
}
