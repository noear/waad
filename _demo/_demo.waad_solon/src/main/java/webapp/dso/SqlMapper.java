package webapp.dso;

import java.util.*;

import org.noear.waad.BaseMapper;

import webapp.model.AppxModel;

/**
 * xml sql mapper
 * */
@Namespace("webapp.dso")
public interface SqlMapper extends BaseMapper<AppxModel> {
    //随便取条数据的ID
    int appx_get() throws Exception;

    //根据id取条数据
    AppxModel appx_get2(int app_id) throws Exception;

    //取一批ID
    Map<String,Object> appx_get3(String tb, int app_id) throws Exception;

    List<AppxModel> appx_getlist(int app_id) throws Exception;

    List<Integer> appx_getids() throws Exception;
}
