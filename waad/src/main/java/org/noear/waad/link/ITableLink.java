package org.noear.waad.link;

/**
 * @author noear
 * @since 4.0
 */
public class ITableLink<T> implements ITable<T> {
    private final ITableSpecLink ___ITableSpec;

    public ITableLink(String name, String asName) {
        if(asName == null && name.indexOf(' ') <0) {
            asName = name;
        }

        ___ITableSpec = new ITableSpecLink(name, asName);
    }

    @Override
    public ITableSpec ____getTableSpec() {
        return ___ITableSpec;
    }

    @Override
    public T as(String asName) {
        throw new UnsupportedOperationException();
    }
}
