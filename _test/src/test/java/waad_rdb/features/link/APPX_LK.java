package waad_rdb.features.link;

import org.noear.waad.linq.IColumn;
import org.noear.waad.linq.IColumnLinq;
import org.noear.waad.linq.ITableLinq;

/**
 * @author noear 2024/9/27 created
 */
public class APPX_LK extends ITableLinq<APPX_LK> {
    public static final APPX_LK APPX = new APPX_LK(null);

    private APPX_LK(String asName) {
        super("appx", asName);
    }

    @Override
    public APPX_LK as(String asName) {
        return new APPX_LK(asName);
    }

    public final IColumn APP_ID = new IColumnLinq(this, "app_id");
    public final IColumn AGROUP_ID = new IColumnLinq(this, "agroup_id");
}
