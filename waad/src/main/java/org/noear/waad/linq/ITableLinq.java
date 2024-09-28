package org.noear.waad.linq;

/**
 * @author noear
 * @since 4.0
 */
public class ITableLinq<T> implements ITable<T> {
    private final ITableSpecLinq ___ITableSpec;

    public ITableLinq(String name, String asName) {
        if(asName == null && name.indexOf(' ') <0) {
            asName = name;
        }

        ___ITableSpec = new ITableSpecLinq(name, asName);
    }

    @Override
    public ITableSpec __getTableSpec() {
        return ___ITableSpec;
    }

    @Override
    public T as(String asName) {
        throw new UnsupportedOperationException();
    }
}
