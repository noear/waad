package org.noear.waad;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author noear 2024/9/28 created
 */
public interface IDataList extends Iterable<IDataItem> {
    int size();

    boolean isEmpty();

    IDataItem get(int index);

    void add(IDataItem item);

    /////////////////////////////////

    IDataItem getFirst();

    IDataItem getLast();

    <T> List<T> toEntityList(Class<T> clz);

    Map<String, Object> toMap(String keyColumn);

    Map<String, Object> toMap(String keyColumn, String valColumn);

    <T> Set<T> toSet(String column);

    <T> Set<T> toSet(int columnIndex);

    <T> List<T> toArray(String columnName);

    <T> List<T> toArray(int columnIndex);


    List<IDataItem> getItemList();

    default List<Map<String, Object>> getMapList() {
        List<Map<String, Object>> list = new ArrayList<>(this.size());
        for (IDataItem r : this) {
            list.add(r);
        }
        return list;
    }
}