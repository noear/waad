package waad_rdb.features.link;

import org.noear.waad.link.IColumn;
import org.noear.waad.link.IColumnLink;
import org.noear.waad.link.ITableLink;

/**
 * @author noear 2024/9/27 created
 */
public class TEST_LK extends ITableLink<TEST_LK> {
    public static final TEST_LK TEST = new TEST_LK(null);

    private TEST_LK(String asName) {
        super("test", asName);
    }

    @Override
    public TEST_LK as(String asName) {
        return new TEST_LK(asName);
    }

    public final IColumn V1 = new IColumnLink(this, "v1");
    public final IColumn ID = new IColumnLink(this, "id");
}
