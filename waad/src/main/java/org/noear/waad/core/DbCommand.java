package org.noear.waad.core;

import org.noear.waad.DbContext;

import java.util.List;
import java.util.Map;

/**
 * 数据库执行命令
 *
 * @author noear
 * @since 4.0
 */
public interface DbCommand {
    /**
     * 命令tag（用于寄存一些数据）
     */
    String tag();

    /**
     * 命令id
     */
    String key();

    /**
     * 命令文本
     */
    String text();

    /**
     * 命令参数
     */
    List<Object> args();

    /**
     * 命令参数-map 形式
     */
    Map<String, Object> argsMap();


    /**
     * 是否为批处理
     */
    boolean isBatch();

    /**
     * 是否进行日志（def:0  no:-1 yes:1）
     */
    int isLog();

    /**
     * 执行时间简隔
     */
    long timespan();

    /**
     * 数据库上下文（肯定且必须有）
     */
    DbContext context();

    /**
     * 获取 Sql 字符串（完整的）
     */
    String getSqlString();

    /**
     * 获取 Cmd 字符串（配参数配合，用于执行）
     */
    String getCmdString();
}