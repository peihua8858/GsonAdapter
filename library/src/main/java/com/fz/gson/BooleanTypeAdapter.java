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
 * Boolean 类型适配
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2020/6/30 16:12
 */
public class BooleanTypeAdapter implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

    @Override
    public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null) {
            return false;
        } else {
            try {//直接解析
                return "true".equalsIgnoreCase(json.getAsString());
            } catch (Exception e) {
                return false;
            }
        }
    }
}
