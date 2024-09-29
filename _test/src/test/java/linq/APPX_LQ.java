package linq;

import org.noear.waad.linq.IColumn;
import org.noear.waad.linq.IColumnLinq;
import org.noear.waad.linq.ITableLinq;

/**
 * @author noear 2024/9/27 created
 */
public class APPX_LQ extends ITableLinq<APPX_LQ> {
    public static final APPX_LQ APPX = new APPX_LQ(null);

    private APPX_LQ(String asName) {
        super("appx", asName);
    }

    @Override
    public APPX_LQ as(String asName) {
        return new APPX_LQ(asName);
    }

    public final IColumn APP_ID = new IColumnLinq(this, "app_id");
    public final IColumn AGROUP_ID = new IColumnLinq(this, "agroup_id");
}
