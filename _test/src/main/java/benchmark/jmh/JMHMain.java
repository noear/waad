package benchmark.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;
import benchmark.jmh.jdbc.JdbcService;
import benchmark.jmh.waad.WaadService;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * 性能测试入口,数据是Throughput，越大越好
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class JMHMain {
    JdbcService jdbcService = null;
    WaadService waadService = null;

    @Setup
    public void init() {

        jdbcService = new JdbcService();
        jdbcService.init();

        waadService = new WaadService();
        waadService.init();

    }


    /*   JDBC,基准，有些方法性能飞快    */
    @Benchmark
    public void jdbcInsert() {
        jdbcService.addEntity();
    }

    @Benchmark
    public void jdbcSelectById() {
        jdbcService.getEntity();
    }

    @Benchmark
    public void jdbcExecuteJdbc() {
        jdbcService.executeJdbcSql();
    }


    /*   Waad    */
    @Benchmark
    public void waadInsert() {
        waadService.addEntity();
    }

    @Benchmark
    public void waadSelectById() {
        waadService.getEntity();
    }

    @Benchmark
    public void waadLambdaQuery() {
        waadService.lambdaQuery();
    }

    @Benchmark
    public void waadExecuteJdbc() {
        waadService.executeJdbcSql();
    }

    @Benchmark
    public void waadExecuteJdbc2() throws SQLException {
        waadService.executeJdbcSql2();
    }

    @Benchmark
    public void waadExecuteTemplate() {
        waadService.executeTemplateSql();
    }

    @Benchmark
    public void waadExecuteTemplate2() throws SQLException{
        waadService.executeTemplateSql2();
    }

    @Benchmark
    public void waadFile() {
        waadService.sqlFile();
    }

    @Benchmark
    public void waadPageQuery() {
        waadService.pageQuery();
    }


    public static void main(String[] args) throws RunnerException {

          test();
//        Options opt = new
//                OptionsBuilder()
//                .include(JMHMain.class.getSimpleName())
//                .build();
//        new Runner(opt).run();
    }

    /**
     * 先单独运行一下保证每个测试都没有错误
     */
    public static void test() {
        JMHMain jmhMain = new JMHMain();
        jmhMain.init();
        for (int i = 0; i < 3; i++) {
            Method[] methods = jmhMain.getClass().getMethods();
            for (Method method : methods) {
                if (method.getAnnotation(Benchmark.class) == null) {
                    continue;
                }
                try {

                    method.invoke(jmhMain, new Object[0]);

                } catch (Exception ex) {
                    throw new IllegalStateException(" method " + method.getName(), ex);
                }

            }
        }

    }


}
