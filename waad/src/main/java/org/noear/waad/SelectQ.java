package org.noear.waad;

import org.noear.waad.core.SQLBuilder;

public class SelectQ extends SQLBuilder {
    protected SelectQ(SQLBuilder sqlBuilder){
        builder.append(sqlBuilder.builder);
        paramS.addAll(sqlBuilder.paramS);
    }
}
