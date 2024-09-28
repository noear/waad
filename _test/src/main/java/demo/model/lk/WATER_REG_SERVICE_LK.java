package demo.model.lk;


            
import org.noear.waad.linq.IColumn;
import org.noear.waad.linq.IColumnLinq;
import org.noear.waad.linq.ITableLinq;

public class WATER_REG_SERVICE_LK extends ITableLinq<WATER_REG_SERVICE_LK> {
  public static final WATER_REG_SERVICE_LK WATER_REG_SERVICE = new WATER_REG_SERVICE_LK(null);

  private WATER_REG_SERVICE_LK(String asName) {
    super("water_reg_service", asName);
  }

  @Override
  public WATER_REG_SERVICE_LK as(String asName) {
    return new WATER_REG_SERVICE_LK(asName);
  }

  
  public  final IColumn SERVICE_ID = new IColumnLinq(this,"service_id");  /** md5(name+‘#’+address) */

  public  final IColumn KEY = new IColumnLinq(this,"key");
  public  final IColumn NAME = new IColumnLinq(this,"name");  /** 版本号 */

  public  final IColumn VER = new IColumnLinq(this,"ver");
  public  final IColumn ADDRESS = new IColumnLinq(this,"address");  /** 源信息 */

  public  final IColumn META = new IColumnLinq(this,"meta");
  public  final IColumn NOTE = new IColumnLinq(this,"note");
  public  final IColumn ALARM_MOBILE = new IColumnLinq(this,"alarm_mobile");
  public  final IColumn ALARM_SIGN = new IColumnLinq(this,"alarm_sign");  /** 0:待检查；1检查中 */

  public  final IColumn STATE = new IColumnLinq(this,"state");
  public  final IColumn CODE_LOCATION = new IColumnLinq(this,"code_location");  /** 检查方式（0被检查；1自己签到） */

  public  final IColumn CHECK_TYPE = new IColumnLinq(this,"check_type");  /** 状态检查地址 */

  public  final IColumn CHECK_URL = new IColumnLinq(this,"check_url");  /** 最后检查时间 */

  public  final IColumn CHECK_LAST_TIME = new IColumnLinq(this,"check_last_time");  /** 最后检查状态（0：OK；1：error） */

  public  final IColumn CHECK_LAST_STATE = new IColumnLinq(this,"check_last_state");  /** 最后检查描述 */

  public  final IColumn CHECK_LAST_NOTE = new IColumnLinq(this,"check_last_note");  /** 检测异常数量 */

  public  final IColumn CHECK_ERROR_NUM = new IColumnLinq(this,"check_error_num");  /** 是否为不稳定的 */

  public  final IColumn IS_UNSTABLE = new IColumnLinq(this,"is_unstable");  /** 是否为已启用 */

  public  final IColumn IS_ENABLED = new IColumnLinq(this,"is_enabled");
}

        