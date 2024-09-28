package org.noear.waad.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by noear on 14-9-10.
 */
public interface DataList extends Iterable<DataRow>, Serializable {
    int size();

    boolean isEmpty();

    DataRow get(int index);

    void add(DataRow item);

    /////////////////////////////////

    DataRow getFirst();

    DataRow getLast();

    <T> List<T> toEntityList(Class<T> clz);

    Map<String, Object> toMap(String keyColumn);

    Map<String, Object> toMap(String keyColumn, String valColumn);

    <T> Set<T> toSet(String column);

    <T> Set<T> toSet(int columnIndex);

    <T> List<T> toArray(String columnName);

    <T> List<T> toArray(int columnIndex);

    /////////////////////////////////

    List<DataRow> getRowList();

    default List<Map<String, Object>> getMapList() {
        List<Map<String, Object>> list = new ArrayList<>(this.size());
        for (DataRow r : this) {
            list.add(r);
        }
        return list;
    }

    static DataList create() {
        return new DataListImpl();
    }
}