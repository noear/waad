package waad_rdb.features.link;

import org.noear.waad.link.IColumn;
import org.noear.waad.link.IColumnImpl;
import org.noear.waad.link.ITableImpl;

/**
 * @author noear 2024/9/27 created
 */
public class APPX_LK extends ITableImpl<APPX_LK> {
    protected APPX_LK(String asName) {
        super("appx", asName);
    }

    @Override
    public APPX_LK as(String asName) {
        return new APPX_LK(asName);
    }

    public final IColumn APP_ID = new IColumnImpl(this, "app_id");
    public final IColumn AGROUP_ID = new IColumnImpl(this, "agroup_id");
}
