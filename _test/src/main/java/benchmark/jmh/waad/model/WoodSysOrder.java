package benchmark.jmh.waad.model;

import lombok.Data;
import org.noear.waad.annotation.PrimaryKey;
import org.noear.waad.annotation.Table;

@Data
@Table("sys_order")
public class WoodSysOrder {
    @PrimaryKey
    private Integer id;
    private String name;
    private Integer customerId;
}
