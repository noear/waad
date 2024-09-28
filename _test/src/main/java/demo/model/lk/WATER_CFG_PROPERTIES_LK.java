package demo.model.lk;


            
import org.noear.waad.link.IColumn;
import org.noear.waad.link.IColumnLink;
import org.noear.waad.link.ITableLink;

public class WATER_CFG_PROPERTIES_LK extends ITableLink<WATER_CFG_PROPERTIES_LK>{
  public static final WATER_CFG_PROPERTIES_LK WATER_CFG_PROPERTIES = new WATER_CFG_PROPERTIES_LK(null);

  private WATER_CFG_PROPERTIES_LK(String asName) {
    super("water_cfg_properties", asName);
  }

  @Override
  public WATER_CFG_PROPERTIES_LK as(String asName) {
    return new WATER_CFG_PROPERTIES_LK(asName);
  }

  
  public  final IColumn ROW_ID = new IColumnLink(this,"row_id");  /** 分组标签 */

  public  final IColumn TAG = new IColumnLink(this,"tag");  /** 属性key */

  public  final IColumn KEY = new IColumnLink(this,"key");  /** 类型：0:未知，1:数据库；2:Redis；3:MangoDb; 4:Memcached */

  public  final IColumn TYPE = new IColumnLink(this,"type");  /** 属性值 */

  public  final IColumn VALUE = new IColumnLink(this,"value");
  public  final IColumn EDIT_MODE = new IColumnLink(this,"edit_mode");  /** 是否可编辑 */

  public  final IColumn IS_EDITABLE = new IColumnLink(this,"is_editable");  /** 是否启用 */

  public  final IColumn IS_ENABLED = new IColumnLink(this,"is_enabled");  /** 更新时间 */

  public  final IColumn UPDATE_FULLTIME = new IColumnLink(this,"update_fulltime");
}

        