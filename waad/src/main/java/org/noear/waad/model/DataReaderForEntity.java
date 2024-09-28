package org.noear.waad.model;

import java.sql.SQLException;

/**
 * @author noear 2024/7/12 created
 */
public class DataReaderForEntity<T> implements DataReader<T> {
    private DataReaderForDataRow reader;
    private Class<T> clazz;

    public DataReaderForEntity(DataReaderForDataRow reader, Class<T> clazz) {
        this.reader = reader;
        this.clazz = clazz;
    }

    @Override
    public T next() throws SQLException {
        DataRow row = reader.next();

        if (row != null) {
            return row.toEntity(clazz);
        } else {
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
