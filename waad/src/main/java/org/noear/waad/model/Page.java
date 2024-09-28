package org.noear.waad.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author noear 2021/11/11 created
 */
public interface Page<T> extends Serializable {
    /**
     * 记录
     * */
    List<T> getList();

    /**
     * 总记录数
     * */
    long getTotal();

    /**
     * 页长
     * */
    int getSize();

    /**
     * 总而数
     * */
    long getPages();
}
