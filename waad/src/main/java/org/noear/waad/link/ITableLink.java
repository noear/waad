package org.noear.waad.link;

/**
 * @author noear 2024/9/27 created
 */
public class ITableLink<T> implements ITable<T> {
    private final ITableSpecImpl ___ITableSpec;

    public ITableLink(String name, String asName) {
        if(asName == null && name.indexOf(' ') <0) {
            asName = name;
        }

        ___ITableSpec = new ITableSpecImpl(name, asName);
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
