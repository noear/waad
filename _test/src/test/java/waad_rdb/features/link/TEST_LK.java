package waad_rdb.features.link;

import org.noear.waad.linq.IColumn;
import org.noear.waad.linq.IColumnLinq;
import org.noear.waad.linq.ITableLinq;

/**
 * @author noear 2024/9/27 created
 */
public class TEST_LK extends ITableLinq<TEST_LK> {
    public static final TEST_LK TEST = new TEST_LK(null);

    private TEST_LK(String asName) {
        super("test", asName);
    }

    @Override
    public TEST_LK as(String asName) {
        return new TEST_LK(asName);
    }

    public final IColumn V1 = new IColumnLinq(this, "v1");
    public final IColumn ID = new IColumnLinq(this, "id");
}
