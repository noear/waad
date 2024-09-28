package waad_rdb.features.link;

import org.noear.waad.link.IColumn;
import org.noear.waad.link.IColumnLink;
import org.noear.waad.link.ITableLink;

/**
 * @author noear 2024/9/27 created
 */
public class APPX_LK extends ITableLink<APPX_LK> {
    public static final APPX_LK APPX = new APPX_LK(null);

    private APPX_LK(String asName) {
        super("appx", asName);
    }

    @Override
    public APPX_LK as(String asName) {
        return new APPX_LK(asName);
    }

    public final IColumn APP_ID = new IColumnLink(this, "app_id");
    public final IColumn AGROUP_ID = new IColumnLink(this, "agroup_id");
}
