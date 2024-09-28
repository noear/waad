package demo.model.db;

import java.util.Date;
import org.noear.waad.annotation.*;

            
import lombok.Data;

@Data
@Table("water_reg_service")
public class WaterRegServiceDo{
  @PrimaryKey
  private Integer service_id;
  /** md5(name+‘#’+address) */
  private String key;
  private String name;
  /** 版本号 */
  private String ver;
  private String address;
  /** 源信息 */
  private String meta;
  private String note;
  private String alarm_mobile;
  private String alarm_sign;
  /** 0:待检查；1检查中 */
  private Integer state;
  private String code_location;
  /** 检查方式（0被检查；1自己签到） */
  private Integer check_type;
  /** 状态检查地址 */
  private String check_url;
  /** 最后检查时间 */
  private Date check_last_time;
  /** 最后检查状态（0：OK；1：error） */
  private Integer check_last_state;
  /** 最后检查描述 */
  private String check_last_note;
  /** 检测异常数量 */
  private Integer check_error_num;
  /** 是否为不稳定的 */
  private Integer is_unstable;
  /** 是否为已启用 */
  private Integer is_enabled;

}

        