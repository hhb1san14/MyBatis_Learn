package com.hhb.pojo;

import com.hhb.util.ParameterMapping;

import java.util.List;

/**
 * @author hhb
 * @date 2022/4/13
 * @description
 */
public class BoundSql {

    private String parseSql;

    private List<ParameterMapping> paramsList;

    public BoundSql() {
    }

    public BoundSql(String parseSql, List<ParameterMapping> paramsList) {
        this.parseSql = parseSql;
        this.paramsList = paramsList;
    }

    public String getParseSql() {
        return parseSql;
    }

    public void setParseSql(String parseSql) {
        this.parseSql = parseSql;
    }

    public List<ParameterMapping> getParamsList() {
        return paramsList;
    }

    public void setParamsList(List<ParameterMapping> paramsList) {
        this.paramsList = paramsList;
    }
}
