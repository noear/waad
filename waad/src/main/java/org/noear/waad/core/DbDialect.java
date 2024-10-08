package org.noear.waad.core;

import org.noear.waad.*;
import org.noear.waad.model.DataRow;
import org.noear.waad.util.function.Fun1;

import java.sql.*;

/**
 * 数据库方言定义
 *
 * @author noear
 * @since 3.2
 * @since 4.0
 * */
public interface DbDialect {
    /**
     * 数据类型
     */
    DbType dbType();

    /**
     * 预转换数据（如：SqlTime 转为 Date）
     */
    Object preChange(Object val) throws SQLException;

    /**
     * 预评审代码
     */
    String preReview(String code);

    /**
     * 获取所有的表
     */
    ResultSet getTables(DatabaseMetaData md, String catalog, String schema) throws SQLException;

    /**
     * 是否支持变量分页
     */
    boolean supportsVariablePaging();

    /**
     * 是否支持生成主键
     */
    boolean supportsInsertGeneratedKey();

    /**
     * 排除格式化
     */
    boolean excludeFormat(String str);

    /**
     * 架构名格式化
     */
    String schemaFormat(String name);

    /**
     * 表名格式化
     */
    String tableFormat(String name);

    /**
     * 列名格式化
     */
    String columnFormat(String name);


    /**
     * 分页查询代码构建
     */
    void buildSelectRangeCode(DbContext ctx, String table1, SQLBuilder sqlB, StringBuilder orderBy, int start, int size);

    /**
     * 顶部查询代码构建
     */
    void buildSelectTopCode(DbContext ctx, String table1, SQLBuilder sqlB, StringBuilder orderBy, int size);


    /**
     * 单条插入代码构建
     */
    void buildInsertOneCode(DbContext ctx, String table1, SQLBuilder sqlB, Fun1<Boolean, String> isSqlExpr, boolean _usingNull, DataRow values);


    /**
     * 插入命令
     */
    void insertCmd(StringBuilder sb, String table1);

    /**
     * 更新指令开始
     */
    void updateCmdBegin(StringBuilder sb, String table1);

    /**
     * 更新指令设置
     */
    void updateCmdSet(StringBuilder sb, String table1);

    /**
     * 删除指令
     */
    void deleteCmd(StringBuilder sb, String table1, boolean addFrom);

}