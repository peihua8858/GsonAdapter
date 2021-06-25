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
 * Long 类型适配,如果字符串是小数，则四舍五入
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2020/6/30 16:14
 */
public class LongTypeAdapter implements JsonSerializer<Long>, JsonDeserializer<Long> {

    @Override
    public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

    @Override
    public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null) {
            return 0L;
        } else {
            try {//直接解析
                if ("".equals(json.getAsString()) || "null".equalsIgnoreCase(json.getAsString())) {
                    //定义为int类型,如果后台返回""或者null,则返回0
                    return 0L;
                }
                return toLong(json.getAsString());
            } catch (Exception e) {
                return 0L;
            }
        }
    }

    /**
     * 将Object对象转成Long类型
     *
     * @param value
     * @return 如果value不能转成Long，则默认defaultValue
     */
    private static Long toLong(Object value) {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            try {
                return Math.round(Double.parseDouble((String) value));
            } catch (NumberFormatException ignored) {
            }
        }
        return 0L;
    }
}
