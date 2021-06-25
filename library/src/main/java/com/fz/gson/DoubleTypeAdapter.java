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
 * Double 类型适配
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2020/6/30 16:13
 */
public class DoubleTypeAdapter implements JsonSerializer<Double>, JsonDeserializer<Double> {


    @Override
    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

    @Override
    public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null) {
            return 0.0;
        } else {
            try {//直接解析
                if ("".equals(json.getAsString()) || "null".equalsIgnoreCase(json.getAsString())) {
                    //定义为int类型,如果后台返回""或者null,则返回0
                    return 0.0;
                }
                return Double.parseDouble(json.getAsString());
            } catch (Exception e) {
                return 0.0;
            }
        }
    }
}
