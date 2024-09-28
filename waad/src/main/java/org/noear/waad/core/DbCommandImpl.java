package org.noear.waad.core;

import org.noear.waad.DbContext;
import org.noear.waad.transaction.DbTran;
import org.noear.waad.transaction.DbTranUtil;
import org.noear.waad.cache.ICacheService;
import org.noear.waad.util.function.Act1;

import java.util.*;

/**
 * 数据库执行命令实现
 *
 * @author noear
 * @since 14-9-5.
 * @since 4.0
 */
public class DbCommandImpl implements DbCommand {
    public String tag;
    public String key;
    public String text;
    public List<Object> args;
    private Map<String, Object> _argsMap;

    public boolean isBatch = false;
    public int isLog; //def:0  no:-1 yes:1

    public ICacheService cache;

    public Map<String,Object> attachment;

    //计时变量
    public long timestart = 0;
    public long timestop = 0;

    /**
     * 受影响的行数，一次可能执行多条 SQL，所以是一个数组
     */
    public long[] affectRow;

    public final DbContext context;
    public final DbTran tran;

    public DbCommandImpl(DbContext context) {
        this.context = context;
        this.context.lastCommand = this;
        this.tran = DbTranUtil.current();
    }

    @Override
    public String tag() {
        return tag;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public String text() {
        return text;
    }

    @Override
    public List<Object> args() {
        return args;
    }

    /**
     * 参数字典
     */
    public Map<String, Object> argsMap() {
        if (_argsMap == null) {
            _argsMap = new LinkedHashMap<>();

            int idx = 0;
            for (Object v : args) {
                _argsMap.put("v" + idx, v);
                idx++;
            }
        }

        return _argsMap;
    }

    @Override
    public boolean isBatch() {
        return isBatch;
    }

    @Override
    public int isLog() {
        return isLog;
    }

    /**
     * 执行时长
     */
    @Override
    public long timespan() {
        return timestop - timestart;
    }

    @Override
    public DbContext context() {
        return context;
    }

    /**
     * 转为SQL字符串
     */
    public String getSqlString() {
        StringBuilder sb = new StringBuilder();

        if (isBatch) {
            sb.append(text);
            sb.append(" --:batch");
        } else {
            String[] ss = text.split("\\?");
            for (int i = 0, len = ss.length, len2 = args.size(); i < len; i++) {
                sb.append(ss[i]);

                if (i < len2) {
                    Object val = args.get(i);

                    if (val == null) {
                        sb.append("NULL");
                    } else if (val instanceof String) {
                        sb.append("'").append(val).append("'");
                    } else if (val instanceof Boolean) {
                        sb.append(val);
                    } else if (val instanceof Date) {
                        sb.append("'").append(val).append("'");
                    } else {
                        sb.append(val);
                    }
                }
            }
        }

        return sb.toString();
    }

    /**
     * 完整的命令文本
     */
    public String getCmdString() {
        if (context.codeHint() == null)
            return context.metaData().getDialect().preReview(text);
        else
            return context.codeHint() + context.metaData().getDialect().preReview(text);
    }

    public Act1<DbCommandImpl> onExecuteAft = null;

}
