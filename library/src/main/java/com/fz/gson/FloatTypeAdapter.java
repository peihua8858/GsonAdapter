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
 * Float 类型适配
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2020/6/30 16:13
 */
public class FloatTypeAdapter implements JsonSerializer<Float>, JsonDeserializer<Float> {


    @Override
    public JsonElement serialize(Float src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

    @Override
    public Float deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null) {
            return 0f;
        } else {
            try {//直接解析
                if ("".equals(json.getAsString()) || "null".equalsIgnoreCase(json.getAsString())) {
                    //定义为int类型,如果后台返回""或者null,则返回0
                    return 0f;
                }
                return Float.parseFloat(json.getAsString());
            } catch (Exception e) {
                return 0f;
            }
        }
    }
}
