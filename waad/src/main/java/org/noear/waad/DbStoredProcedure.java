package org.noear.waad;

import org.noear.waad.core.DbCommandImpl;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by noear on 14-6-12.
 * 存储过程访问类
 */
public class DbStoredProcedure extends DbProcedure {

    public DbStoredProcedure(DbContext context){
        super(context);
    }

    protected DbStoredProcedure call(String storedProcedure) {
        this.commandText = storedProcedure;
        this.args.clear();
        this._waadKey = null;

        return this;
    }

    @Override
    public DbProcedure set(String param, Object value) {
        doSet(param, value);
        return this;
    }

    @Override
    public DbProcedure setMap(Map<String, Object> map) {
        throw new RuntimeException("DbStoredProcedure not support setMap");
    }

    @Override
    public DbProcedure setEntity(Object obj) {
        throw new RuntimeException("DbStoredProcedure not support setEntity");
    }

    @Override
    protected String getCommandID() {
        tryLazyload();

        return this.commandText;
    }

    @Override
    protected DbCommandImpl getCommand(){
        tryLazyload();

        DbCommandImpl cmd = new DbCommandImpl(this.context);

        cmd.key      = getCommandID();
        cmd.args = this.args;

        StringBuilder sb = new StringBuilder();
        sb.append("{call ");

        sb.append(commandText.trim());

        if(args.size()>0) {
            sb.append('(');
            for (Object p : args) {
                sb.append("?,");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(')');
        }
        sb.append('}');

        cmd.text = sb.toString();

        runCommandBuiltEvent(cmd);

        return cmd;
    }

    @Override
    public int execute() throws SQLException {
        tryLazyload();
        return super.execute();
    }
}
