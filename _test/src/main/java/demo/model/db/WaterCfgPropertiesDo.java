package demo.model.db;

import java.util.Date;
import org.noear.waad.annotation.*;

            
import lombok.Data;

@Data
@Table("water_cfg_properties")
public class WaterCfgPropertiesDo{
  @PrimaryKey
  private Integer row_id;
  /** 分组标签 */
  private String tag;
  /** 属性key */
  private String key;
  /** 类型：0:未知，1:数据库；2:Redis；3:MangoDb; 4:Memcached */
  private Integer type;
  /** 属性值 */
  private String value;
  private String edit_mode;
  /** 是否可编辑 */
  private Boolean is_editable;
  /** 是否启用 */
  private Integer is_enabled;
  /** 更新时间 */
  private Date update_fulltime;

}

        