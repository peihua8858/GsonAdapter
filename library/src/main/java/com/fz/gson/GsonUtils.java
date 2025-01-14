package com.fz.gson;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GsonUtils {
    private GsonUtils() {
        throw new AssertionError();
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return fromJson(GsonFactory.createGson(), json, classOfT);
    }

    public static <T> T fromJson(Gson gson, String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }


    public static <T> T fromJson(String json, Type classOfT) {
        return fromJson(GsonFactory.createGson(), json, classOfT);
    }

    public static <T> T fromJson(Gson gson, String json, Type classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type rawType, Type... typeArguments) {
        return fromJson(GsonFactory.createGson(), json, rawType, typeArguments);
    }

    public static <T> T fromJson(Gson gson, String json, Type rawType, Type... typeArguments) {
        Type apiResponseType = TypeToken.getParameterized(rawType, typeArguments).getType();
        return gson.fromJson(json, apiResponseType);
    }

    public static <T> T fromJson(String json, Object obj, Class<T> rawType) {
        return fromJson(GsonFactory.createGson(), json, obj, rawType);
    }

    public static <T> T fromJson(Gson gson, String json, Object obj, Class<T> rawType) {
        Type type = getSuperclassTypeParameter(obj);
        Type apiResponseType = TypeToken.getParameterized(rawType, type).getType();
        return gson.fromJson(json, apiResponseType);
    }

    public static <T> T fromJson(Reader reader, Type classOfT) {
        return fromJson(GsonFactory.createGson(), reader, classOfT);
    }

    public static <T> T fromJson(Gson gson, Reader reader, Type classOfT) {
        return gson.fromJson(reader, classOfT);
    }

    public static <T> T fromJson(Reader reader, Type rawType, Type... typeArguments) {
        return fromJson(GsonFactory.createGson(), reader, rawType, typeArguments);
    }

    public static <T> T fromJson(Gson gson, Reader reader, Type rawType, Type... typeArguments) {
        Type apiResponseType = TypeToken.getParameterized(rawType, typeArguments).getType();
        return gson.fromJson(reader, apiResponseType);
    }

    public static <T> T fromJson(Reader reader, Object obj, Class<T> rawType) {
        return fromJson(GsonFactory.createGson(), reader, obj, rawType);
    }

    public static <T> T fromJson(Gson gson, Reader reader, Object obj, Class<T> rawType) {
        Type type = getSuperclassTypeParameter(obj);
        Type apiResponseType = TypeToken.getParameterized(rawType, type).getType();
        return gson.fromJson(reader, apiResponseType);
    }

    /**
     * 根据指定对象获取其泛型的实际类型
     * 返回的类型为[java.io.Serializable]。
     *
     * @param obj
     * @return
     * @see {@link $Gson$Types#canonicalize
     */
    public static Type getSuperclassTypeParameter(Object obj) {
        return getSuperclassTypeParameter(obj.getClass());
    }

    /**
     * 根据指定类型获取其泛型的实际类型
     * 返回的类型为[java.io.Serializable]。
     *
     * @param subclass
     * @return
     * @see {@link $Gson$Types#canonicalize
     */
    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (!(superclass instanceof ParameterizedType)) {
            throw new RuntimeException("Missing type parameter.");
        }
        return getParameterUpperBound(0, (ParameterizedType) superclass);
    }

    /**
     * 根据传入的参数化类型获取实际类型
     * 返回在功能上相等但不一定相等的类型。
     * 返回的类型为[java.io.Serializable]。
     *
     * @param index
     * @param type
     * @return
     * @see {@link $Gson$Types#canonicalize
     */
    public static Type getParameterUpperBound(int index, ParameterizedType type) {
        Type[] types = type.getActualTypeArguments();
        if (index < 0 || index >= types.length) {
            throw new IllegalArgumentException("Index " + index + " not in range [0," + types.length + ") for " + type);
        }
        return $Gson$Types.canonicalize(types[index]);
    }
}