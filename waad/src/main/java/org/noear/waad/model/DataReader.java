package org.noear.waad.model;

import java.sql.SQLException;

/**
 * 数据读取器接口
 *
 * @author noear 2024/7/12 created
 */
public interface DataReader<T> extends AutoCloseable {
    /**
     * 获取下一个（可能为 null）
     */
    T next() throws SQLException;
}
