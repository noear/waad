package waad_rdb.features.link;

import org.noear.waad.link.IColumn;
import org.noear.waad.link.IColumnLink;
import org.noear.waad.link.ITableLink;

/**
 * @author noear 2024/9/27 created
 */
public class APPX_AGROUP_LK extends ITableLink<APPX_AGROUP_LK> {
    public static final APPX_AGROUP_LK APPX_AGROUP = new APPX_AGROUP_LK(null);

    private APPX_AGROUP_LK(String asName) {
        super("appx_agroup", asName);
    }

    @Override
    public APPX_AGROUP_LK as(String asName) {
        return new APPX_AGROUP_LK(asName);
    }

    public final IColumn AGROUP_ID = new IColumnLink(this, "agroup_id");
    public final IColumn NAME = new IColumnLink(this, "name");
}
