package waad_rdb.features.link;

import org.noear.waad.link.IColumn;
import org.noear.waad.link.IColumnImpl;
import org.noear.waad.link.ITableImpl;

/**
 * @author noear 2024/9/27 created
 */
public class TEST_LK extends ITableImpl<TEST_LK> {
    protected TEST_LK(String asName) {
        super("test", asName);
    }

    @Override
    public TEST_LK as(String asName) {
        return new TEST_LK(asName);
    }

    public final IColumn V1 = new IColumnImpl(this, "v1");
    public final IColumn ID = new IColumnImpl(this, "id");
}
