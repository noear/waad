package benchmark.jmh;

public interface BaseService {
    /**
     * 简单增加一个实体
     */
    public void addEntity();

    /**
     * 根据主键查询实体
     * @return
     */
    public Object getEntity();

    /**
     * 动态构造sql语句，并支持重构
     * */
    public void lambdaQuery();

    /**
     * 执行jdbc sql
     */
    public void executeJdbcSql();

    /**
     * 执行sql模板或者HQL
     */
    public void executeTemplateSql();

    /**
     * SQL放到文件管理
     */
    public void sqlFile();

    /**
     * 一个一对多的例子
     */
    public void one2Many();

    /**
     * 测试翻页查询
     */
    public void pageQuery();

    /**
     * 通过配置文件来映射复杂的查询结果，目前只有mybatis和beetlsql支持
     */
    public void complexMapping();


}
