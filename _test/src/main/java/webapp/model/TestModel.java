package webapp.model;

import lombok.Data;
import org.noear.waad.annotation.PrimaryKey;
import org.noear.waad.annotation.Table;

//和表名不相同，须注解表名
@Data
@Table("test")
public class TestModel {
    @PrimaryKey
    public Long id;
    public Integer v1;
    public Integer v2;

    public TestModel(){}

    public TestModel(long id, int v1){
        this.id = id;
        this.v1 = v1;
    }
}
