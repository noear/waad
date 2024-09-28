package waad_rdb.features.linq;

import org.noear.waad.linq.IColumn;
import org.noear.waad.linq.IColumnLinq;
import org.noear.waad.linq.ITableLinq;

/**
 * @author noear 2024/9/27 created
 */
public class TEST_LQ extends ITableLinq<TEST_LQ> {
    public static final TEST_LQ TEST = new TEST_LQ(null);

    private TEST_LQ(String asName) {
        super("test", asName);
    }

    @Override
    public TEST_LQ as(String asName) {
        return new TEST_LQ(asName);
    }

    public final IColumn V1 = new IColumnLinq(this, "v1");
    public final IColumn ID = new IColumnLinq(this, "id");
}
