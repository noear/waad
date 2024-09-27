package waad_rdb.features.link;

import org.noear.waad.link.IColumn;
import org.noear.waad.link.IColumnImpl;
import org.noear.waad.link.ITableImpl;

/**
 * @author noear 2024/9/27 created
 */
public class APPX_AGROUP_LK extends ITableImpl<APPX_AGROUP_LK> {
    protected APPX_AGROUP_LK(String asName) {
        super("appx_agroup", asName);
    }

    @Override
    public APPX_AGROUP_LK as(String asName) {
        return new APPX_AGROUP_LK(asName);
    }

    public final IColumn AGROUP_ID = new IColumnImpl(this, "agroup_id");
    public final IColumn NAME = new IColumnImpl(this, "name");
}
