package com.fujfu.template.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.fujfu.template.web.controller.mo.ResponseMO;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ocean on 06/19/2019
 */

public class JsonObjectUtil {

    public static <T> ResponseMO<List<T>> parseList(String json, Class<T> clazz) {
        ParameterizedTypeImpl inner = new ParameterizedTypeImpl(new Type[]{clazz}, null, List.class);
        ParameterizedTypeImpl outer = new ParameterizedTypeImpl(new Type[]{inner}, null, ResponseMO.class);
        return JSONObject.parseObject(json, outer);
    }

    public static <T> ResponseMO<T> parseSingle(String json, Class<T> dataMO) {
        return JSONObject.parseObject(json, new TypeReference<ResponseMO<T>>(dataMO) {
        });
    }
}
