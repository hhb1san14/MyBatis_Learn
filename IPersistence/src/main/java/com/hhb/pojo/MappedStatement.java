package com.hhb.pojo;

/**
 * @author hhb
 * @date 2022/4/12
 * @description 对应SqlMapper.xml的实体
 */
public class MappedStatement {

    //ID标识
    private String id;
    // 结果集
    private String resultType;
    // 请求值类型
    private String paramType;
    //SQL
    private String sql;

    public MappedStatement() {
    }

    public MappedStatement(String id, String resultType, String paramType, String sql) {
        this.id = id;
        this.resultType = resultType;
        this.paramType = paramType;
        this.sql = sql;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
