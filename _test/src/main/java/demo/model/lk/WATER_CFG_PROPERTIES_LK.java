package demo.model.lk;


            
import org.noear.waad.linq.IColumn;
import org.noear.waad.linq.IColumnLinq;
import org.noear.waad.linq.ITableLinq;

public class WATER_CFG_PROPERTIES_LK extends ITableLinq<WATER_CFG_PROPERTIES_LK> {
  public static final WATER_CFG_PROPERTIES_LK WATER_CFG_PROPERTIES = new WATER_CFG_PROPERTIES_LK(null);

  private WATER_CFG_PROPERTIES_LK(String asName) {
    super("water_cfg_properties", asName);
  }

  @Override
  public WATER_CFG_PROPERTIES_LK as(String asName) {
    return new WATER_CFG_PROPERTIES_LK(asName);
  }

  
  public  final IColumn ROW_ID = new IColumnLinq(this,"row_id");  /** 分组标签 */

  public  final IColumn TAG = new IColumnLinq(this,"tag");  /** 属性key */

  public  final IColumn KEY = new IColumnLinq(this,"key");  /** 类型：0:未知，1:数据库；2:Redis；3:MangoDb; 4:Memcached */

  public  final IColumn TYPE = new IColumnLinq(this,"type");  /** 属性值 */

  public  final IColumn VALUE = new IColumnLinq(this,"value");
  public  final IColumn EDIT_MODE = new IColumnLinq(this,"edit_mode");  /** 是否可编辑 */

  public  final IColumn IS_EDITABLE = new IColumnLinq(this,"is_editable");  /** 是否启用 */

  public  final IColumn IS_ENABLED = new IColumnLinq(this,"is_enabled");  /** 更新时间 */

  public  final IColumn UPDATE_FULLTIME = new IColumnLinq(this,"update_fulltime");
}

        