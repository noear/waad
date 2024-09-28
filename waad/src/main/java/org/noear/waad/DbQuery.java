package org.noear.waad;

import org.noear.waad.core.CommandImpl;
import org.noear.waad.core.DataAccess;
import org.noear.waad.core.SQLBuilder;

/**
 * Created by noear on 14-9-5.
 *
 * 查询语句访问类
 *
 * \$.tableName  --$ 代表当表db context schema
 * \@paramName   --@ 为参数名的开头
 */
public class DbQuery extends DataAccess<DbQuery> {

    public DbQuery(DbContext context)
    {
        super(context);
    }

    public DbQuery sql(SQLBuilder sqlBuilder) {
        this.commandText = sqlBuilder.toString();
        this.paramS.clear();
        this._waadKey = null;
        for (Object p1 : sqlBuilder.paramS) {
            doSet("", p1);
        }

        return this;
    }

    @Override
    protected String getCommandID() {
        return this.commandText;
    }

    @Override
    protected CommandImpl getCommand() {

        CommandImpl cmd = new CommandImpl(this.context);

        cmd.key     = getCommandID();
        cmd.args = this.paramS;

        StringBuilder sb = new StringBuilder(commandText);

        cmd.text = sb.toString();

        runCommandBuiltEvent(cmd);

        return cmd;
    }
}
