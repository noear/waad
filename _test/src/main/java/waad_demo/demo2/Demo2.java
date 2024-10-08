package waad_demo.demo2;

import org.noear.waad.DbContext;
import org.noear.waad.TableQuery;
import org.noear.waad.mapper.BaseMapper;
import waad_demo.config.DbConfig;
import waad_demo.mapper.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Demo2 {
    DbContext db = DbConfig.pc_bcf;

    public void test() {
        List<String> ids = new ArrayList<>();
        BaseMapper<UserModel>  tmp = db.mapperBase(UserModel.class);
        tmp.selectList(wq -> wq.and("id IN(?...)", ids));

//        //没参数的
//        db.call("@webapp.dso.SqlMapper.appx_get").getMap();
//
//
//        //有参数的
//        Map<String,Object> args = new HashMap<>();
//        db.call("@webapp.dso.SqlMapper.appx_get").setMap(args).getMap();
    }

    public Object searchBy(Integer id, String name, Integer type) throws Exception {
        TableQuery qr = db.table("user").where("1=1");
        if (id != null) {
            qr.and("id=?", id);
        }

        if (name != null) {
            qr.and("name=?", name);
        }

        if (type != null && type > 2) {
            qr.and("type=?", type);
        }

        return qr.limit(50).selectMapList("*");
    }

    public Object searchBy2(Integer id, String name, Integer type) throws Exception {
        return db.table("user")
                .where("1=1")
                .andIf(id != null, "id=?", id)
                .andIf(name != null, "name=?", name)
                .andIf(type != null && type > 2, "type=?", type)
                .limit(50)
                .selectMapList("*");
    }

    public Object demo1(String name, String akey) throws Exception {
        TableQuery qr = db.table("appx").where("1=1");

        if (name != null) {
            qr.and("name=?", name);
        }

        if (akey != null) {
            qr.and("akey=?", akey);
        }

        return qr.limit(1).selectMapList("*");
    }

    public Object demo1_2(String name, String akey) throws Exception {
        return db.table("appx").where("1=1").build((qr) -> {
            if (name != null) {
                qr.and("name=?", name);
            }

            if (akey != null) {
                qr.and("akey=?", akey);
            }
        }).limit(1).selectMapList("*");
    }

    public Object demo1_3(String name, String akey) throws Exception {
        return db.table("appx")
                .where("1=1")
                .andIf(name != null, "name=?", name)
                .andIf(akey != null, "akey=?", akey)
                .limit(1).selectMapList("*");
    }


    public void demo2(String name, String note, String akey) throws Exception {
        TableQuery qr = db.table("appx").set("log_fulltime", "$NOW()");

        if (name != null) {
            qr.set("name", name);
        }

        if (note != null) {
            qr.set("note", note);
        }

        if (akey != null) {
            qr.set("akey", akey);
        }

        qr.insert();
    }

    public void demo2_2(String name, String note, String akey) throws Exception {
        db.table("appx").set("log_fulltime", "$NOW()").build(qr -> {
            if (name != null) {
                qr.set("name", name);
            }

            if (note != null) {
                qr.set("note", note);
            }

            if (akey != null) {
                qr.set("akey", akey);
            }
        }).insert();
    }

    public void demo2_3(String name, String note, String akey) throws Exception {
        db.table("appx")
                .set("log_fulltime", "$NOW()")
                .setIf(name != null, "name", name)
                .setIf(note != null, "note", note)
                .setIf(akey != null, "akey", akey)
                .insert();
    }

    public void insert(Map<String, Object> map) throws Exception {
        TableQuery qr = db.table("user");
        map.forEach((k, v) -> {
            if (v != null) {
                qr.set(k, v);
            }
        });
        qr.insert();
    }

    public void insert2(Map<String, Object> map) throws Exception {
        db.table("user").setMapIf(map, (k, v) -> v != null).insert();
    }
}
