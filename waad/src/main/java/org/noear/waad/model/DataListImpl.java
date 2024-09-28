package org.noear.waad.model;

import org.noear.waad.wrap.ClassWrap;

import java.io.Serializable;
import java.util.*;

/**
 * Created by noear on 14-9-10.
 *
 * getRow,addRow,getRowCount 是为跨平台设计的接口，不能去掉
 * 不能转为继承自List
 * 否则，嵌入别的引擎时，会变转为不可知的ListAdapter，让扩展的方法失效
 */
class DataListImpl implements DataList, Serializable {
    private ArrayList<DataRow> items = new ArrayList<>();

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public DataRow get(int index) {
        return items.get(index);
    }

    @Override
    public void add(DataRow item) {
        items.add(item);
    }

    @Override
    public List<DataRow> getItemList() {
        return items;
    }

    @Override
    public Iterator<DataRow> iterator() {
        return items.iterator();
    }

    ///////////////////////////////////

    public DataRow getFirst() {
        if (size() > 0) {
            return get(0);
        } else {
            return null;
        }
    }

    public DataRow getLast() {
        if (size() > 0) {
            return get(size() - 1);
        } else {
            return null;
        }
    }

    //----------

    /**
     * 将所有列转为类做为数组的数据
     */
    public <T> List<T> toEntityList(Class<T> clz) {
        ClassWrap classWrap = ClassWrap.get(clz);
        List<T> list = new ArrayList<T>(size());

        for (DataRow r : this) {
            T item = classWrap.toEntity(r);
            list.add((T) item);
        }

        return list;
    }

    /**
     * 选1列做为MAP的key，并把行数据做为val
     */
    public Map<String, Object> toMap(String keyColumn) {
        return toMap(keyColumn, null);
    }

    /**
     * 选两列做为MAP的数据
     */
    public Map<String, Object> toMap(String keyColumn, String valColumn) {
        Map<String, Object> map = new HashMap<>();

        if (valColumn == null || valColumn.length() == 0) {
            for (DataRow r : this) {
                map.put(r.get(keyColumn).toString(), r);
            }
        } else {
            for (DataRow r : this) {
                map.put(r.get(keyColumn).toString(), r.get(valColumn));
            }
        }

        return map;
    }

    /**
     * 选一列做为SET的数据
     */
    public <T> Set<T> toSet(String column) {
        Set<T> set = new HashSet<>();

        for (DataRow r : this) {
            set.add((T) r.get(column));
        }
        return set;
    }

    public <T> Set<T> toSet(int columnIndex) {
        Set<T> set = new HashSet<>();

        for (DataRow r : this) {
            set.add((T) r.get(columnIndex));
        }
        return set;
    }


    /**
     * 选一列做为数组的数据
     */
    public <T> List<T> toArray(String columnName) {
        List<T> list = new ArrayList<T>();

        for (DataRow r : this) {
            list.add((T) r.get(columnName));
        }
        return list;
    }

    /**
     * 选一列做为数组的数据
     */
    public <T> List<T> toArray(int columnIndex) {
        List<T> list = new ArrayList<T>();

        for (DataRow r : this) {
            list.add((T) r.get(columnIndex));
        }
        return list;
    }
}
