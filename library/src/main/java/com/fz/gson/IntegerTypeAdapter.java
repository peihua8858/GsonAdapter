package com.fz.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Integer类型适配,如果字符串是小数，则四舍五入
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2020/6/30 16:13
 */
public class IntegerTypeAdapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {


    @Override
    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null) {
            return 0;
        } else {
            try {//直接解析
                if ("".equals(json.getAsString()) || "null".equalsIgnoreCase(json.getAsString())) {
                    //定义为int类型,如果后台返回""或者null,则返回0
                    return 0;
                }
                return toInteger(json.getAsString());
            } catch (Exception e) {
                return 0;
            }
        }
    }

    /**
     * 将Object对象转成Integer类型
     *
     * @param value
     * @return 如果value不能转成Integer，则默认defaultValue
     */
    private Integer toInteger(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return (int) Math.round(Double.parseDouble((String) value));
            } catch (Exception ignored) {
            }
        }
        return 0;
    }
}
