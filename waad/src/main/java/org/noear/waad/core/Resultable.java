package org.noear.waad.core;

import org.noear.waad.cache.CacheUsing;
import org.noear.waad.cache.ICacheController;
import org.noear.waad.model.DataList;
import org.noear.waad.model.DataRow;
import org.noear.waad.model.DataReaderForDataRow;
import org.noear.waad.model.Variate;
import org.noear.waad.util.function.Act2;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 可结果的
 *
 * @author noear
 * @since 14/11/12.
 * @since 4.0
 */
public interface Resultable extends ICacheController<Resultable> {
     long getCount() throws SQLException;
     Object getValue() throws SQLException;
     <T> T getValue(T def) throws SQLException;

     Variate getVariate() throws SQLException;
     Variate getVariate(Act2<CacheUsing, Variate> cacheCondition) throws SQLException;

     <T> T getItem(Class<T> cls) throws SQLException;
     <T> T getItem(Class<T> cls, Act2<CacheUsing, T> cacheCondition) throws SQLException;

     <T> List<T> getList(Class<T> cls) throws SQLException;
     <T> List<T> getList(Class<T> cls,Act2<CacheUsing, List<T>> cacheCondition) throws SQLException;

     DataReaderForDataRow getDataReader(int fetchSize) throws SQLException;

     DataList getDataList() throws SQLException;
     DataList getDataList(Act2<CacheUsing, DataList> cacheCondition) throws SQLException;
     DataRow getDataRow() throws SQLException;
     DataRow getDataRow(Act2<CacheUsing, DataRow> cacheCondition) throws SQLException;

     List<Map<String,Object>> getMapList() throws SQLException;
     Map<String,Object> getMap() throws SQLException;

     <T> List<T> getArray(String column) throws SQLException;
     <T> List<T> getArray(int columnIndex) throws SQLException;
     default  <T> List<T> getArray() throws SQLException{
          return getArray(0);
     }
}
