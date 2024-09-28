maven 插件，生成器



|                             |             |                                                                       |
|-----------------------------|-------------|-----------------------------------------------------------------------|
| `${tableName}`              | 表名占位符       | `user_info`                                                           |
| `${tableName_upper}`        | 表名大写占位符     | `USER_INFO`                                                           |
| `${tableName_lower}`        | 表名小写占位符     | `user_info`                                                           |
| `${domainName}`             | 域名占位符（包名）   | `UserInfo`                                                            |
| `${entityName}`             | 实体名占位符      | (`${domainName}Do` -> `UserInfoDo`)                                   |
| `${fields}`                 | 字段占位符       | `private String userName;...`                                         |
| `${fields_public}`          | 字段公有模式占位符   | `public String userName;...`                                          |
| `${fields_getter}`          | 字段获取模式占位符   | `public String getUserName(){}...`                                    |
| `${fields_setter}`          | 字段设置模式占位符   | `public String setUserName(String userName){}...`                     |
|                             |             |                                                                       |
| `${fields_lk}`              | 字段连接占位符     | `public final IColumn AGROUP_ID = new IColumnImpl(this, "agroup_id")` |


### 默认模板

* 数据领域类

```xml
<entityGenerator targetPackage="demo.model.db" entityName="${domainName}Do">
            <![CDATA[
import lombok.Data;

@Data
@Table("${tableName}")
public class ${entityName}{
    ${fields}
}
]]>
</entityGenerator>
```

* 查询连接类

```xml

<entityGenerator targetPackage="demo.model.db" entityName="${tableName_upper}_LK">
    <![CDATA[
import org.noear.waad.linq.IColumn;
import org.noear.waad.linq.IColumnLink;
import org.noear.waad.linq.ITableLink;

public class ${entityName} extends ITableLink<${entityName}>{
  public static final ${entityName} ${tableName_upper} = new ${entityName}(null);

  private ${entityName}(String asName) {
    super("${tableName}", asName);
  }

  @Override
  public ${entityName} as(String asName) {
    return new ${entityName}(asName);
  }
    
  ${fields_lk}
}
]]>
</entityGenerator>
```