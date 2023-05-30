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
 * 字符串类型适配
 * 解析返回非空字符串，如果是空，则返回空字符串
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2020/6/30 16:14
 */
public class StringTypeAdapter implements JsonSerializer<String>, JsonDeserializer<String> {


    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null) {
            return "";
        } else {
            try {//直接解析
                if (json.isJsonArray()) {
                    return json.getAsJsonArray().toString();
                }
                if (json.isJsonObject()) {
                    return json.getAsJsonObject().toString();
                }
                String result = json.getAsString();
                if ("null".equalsIgnoreCase(result)) {
                    return "";
                }
                return json.getAsString();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }
}
