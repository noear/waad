package org.noear.waad.linq;

/**
 * 常见的简单表达式（参考此接口，可自定义一批静态表达式）
 *
 * @author noear
 * @since 4.0
 */
public interface IExprs {
    //:: 聚合函数

    /**
     * 求最大值
     */
    static IExpr max(IColumn column) {
        return new IExprLinq("MAX(?)", column);
    }

    /**
     * 求最小值
     */
    static IExpr min(IColumn column) {
        return new IExprLinq("MIN(?)", column);
    }

    /**
     * 求平均值
     */
    static IExpr avg(IColumn column) {
        return new IExprLinq("AVG(?)", column);
    }

    /**
     * 求和
     */
    static IExpr sum(IColumn column) {
        return new IExprLinq("SUM(?)", column);
    }

    /**
     * 计数
     */
    static IExpr count(IColumn column) {
        return new IExprLinq("COUNT(?)", column);
    }

    //:: 转换函数

    /**
     * 转换类型
     */
    static IExpr convert(String type, IColumn column) {
        return new IExprLinq("CONVERT(?,?)", type, column);
    }

    /**
     * 转换类型
     */
    static IExpr cast(IColumn column, String type) {
        return new IExprLinq("CAST(?,?)", column, type);
    }

    //:: 日期函数

    /**
     * 获取时间中的年
     */
    static IExpr year(IExpr column) {
        return new IExprLinq("YEAR(?)", column);
    }

    /**
     * 获取时间中的月
     */
    static IExpr month(IExpr column) {
        return new IExprLinq("MONTH(?)", column);
    }

    /**
     * 获取时间中的日
     */
    static IExpr day(IExpr column) {
        return new IExprLinq("DAY(?)", column);
    }

    //:: 数字函数

    static IExpr abs(IExpr column) {
        return new IExprLinq("ABS(?)", column);
    }

    //:: 字符串函数

    static IExpr lower(IExpr column) {
        return new IExprLinq("LOWER(?)", column);
    }

    static IExpr upper(IExpr column) {
        return new IExprLinq("UPPER(?)", column);
    }
}