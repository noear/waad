package benchmark.jmh.waad.model;


import lombok.Data;
import org.noear.waad.annotation.PrimaryKey;
import org.noear.waad.annotation.Table;

@Data
@Table("sys_customer")
public class WoodSysCustomer {
    @PrimaryKey
    private Integer id;
    private String code;
    private String name;

    //@FetchMany("customerId")
    //private List<BeetlSysOrder> order;
}
