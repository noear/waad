package linq;

import org.noear.waad.linq.IColumn;
import org.noear.waad.linq.IColumnLinq;
import org.noear.waad.linq.ITableLinq;

/**
 * @author noear 2024/9/27 created
 */
public class APPX_AGROUP_LQ extends ITableLinq<APPX_AGROUP_LQ> {
    public static final APPX_AGROUP_LQ APPX_AGROUP = new APPX_AGROUP_LQ(null);

    private APPX_AGROUP_LQ(String asName) {
        super("appx_agroup", asName);
    }

    @Override
    public APPX_AGROUP_LQ as(String asName) {
        return new APPX_AGROUP_LQ(asName);
    }

    public final IColumn AGROUP_ID = new IColumnLinq(this, "agroup_id");
    public final IColumn NAME = new IColumnLinq(this, "name");
}
