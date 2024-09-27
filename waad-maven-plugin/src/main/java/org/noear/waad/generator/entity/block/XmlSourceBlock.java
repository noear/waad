package org.noear.waad.generator.entity.block;

import java.util.ArrayList;
import java.util.List;

public class XmlSourceBlock {
    public String schema;
    public String url;
    public String username;
    public String password;
    public String driverClassName;
    public String namingStyle;
    public String typeStyle;

    public List<XmlEntityBlock> entityBlocks = new ArrayList<>();
    public List<XmlTableBlock> tableBlocks = new ArrayList<>();
}
