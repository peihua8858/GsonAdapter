package com.fz.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.MapTypeAdapterFactory;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * add by tanping
 * 为了gson 能够正常解析出服务器返回数据 做容错处理。
 */
public class GsonFactory {
    static final boolean DEFAULT_USE_JDK_UNSAFE = true;
    /**
     * 基本类型转换
     */
    public static final BooleanTypeAdapter BOOLEAN = new BooleanTypeAdapter();
    public static final DoubleTypeAdapter DOUBLE = new DoubleTypeAdapter();
    public static final FloatTypeAdapter FLOAT = new FloatTypeAdapter();
    public static final IntegerTypeAdapter INTEGER = new IntegerTypeAdapter();
    public static final LongTypeAdapter LONG = new LongTypeAdapter();
    public static final StringTypeAdapter STRING = new StringTypeAdapter();

    public static GsonBuilder defaultBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        return gsonBuilder;
    }

    public static Gson createDefaultGson() {
        return defaultBuilder().create();
    }

    public static Gson create() {
        return createDefaultBuild().create();
    }

    public static GsonBuilder createDefaultBuild() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //注入 type adapter
        // 集合
        JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(
                new ConstructorConstructor(Collections.<Type, InstanceCreator<?>>emptyMap(),DEFAULT_USE_JDK_UNSAFE));

        ReflectiveTypeAdapterFactory rta = new ReflectiveTypeAdapterFactory(
                new ConstructorConstructor(Collections.<Type, InstanceCreator<?>>emptyMap(),DEFAULT_USE_JDK_UNSAFE),
                FieldNamingPolicy.IDENTITY,
                Excluder.DEFAULT,
                jsonAdapterFactory);
        //Object
        gsonBuilder.registerTypeAdapterFactory(rta);
        gsonBuilder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(Collections.<Type, InstanceCreator<?>>emptyMap(),DEFAULT_USE_JDK_UNSAFE)));
        gsonBuilder.registerTypeAdapterFactory(new MapTypeAdapterFactory(new ConstructorConstructor(Collections.<Type, InstanceCreator<?>>emptyMap(),DEFAULT_USE_JDK_UNSAFE), false));

        //注入 8大基本类型 type adapter
        gsonBuilder.registerTypeAdapter(Double.class, DOUBLE);
        gsonBuilder.registerTypeAdapter(Boolean.class, BOOLEAN);
        gsonBuilder.registerTypeAdapter(Float.class, FLOAT);
        gsonBuilder.registerTypeAdapter(Integer.class, INTEGER);
        gsonBuilder.registerTypeAdapter(Long.class, LONG);
        gsonBuilder.registerTypeAdapter(int.class, INTEGER);
        gsonBuilder.registerTypeAdapter(float.class, FLOAT);
        gsonBuilder.registerTypeAdapter(double.class, DOUBLE);
        gsonBuilder.registerTypeAdapter(boolean.class, BOOLEAN);
        gsonBuilder.registerTypeAdapter(long.class, LONG);
        gsonBuilder.registerTypeAdapter(String.class, STRING);

        return gsonBuilder;
    }

    public static Gson createBuild(Type type, Object typeAdapter) {
        final GsonBuilder builder = createDefaultBuild();
        addTypeAdapter(builder, type, typeAdapter);
        builder.setLenient();
        return builder.create();
    }

    private static void addTypeAdapter(GsonBuilder builder, Type type, Object typeAdapter) {
        if (type != null && (typeAdapter instanceof JsonSerializer<?>
                || typeAdapter instanceof JsonDeserializer<?>
                || typeAdapter instanceof InstanceCreator<?>
                || typeAdapter instanceof TypeAdapter<?>)) {
            builder.registerTypeAdapter(type, typeAdapter);
        }
    }

    public static Gson createBuild(Map<Type, Object> typeAdapters) {
        final GsonBuilder builder = createDefaultBuild();
        if (typeAdapters != null && typeAdapters.size() > 0) {
            Iterator<Map.Entry<Type, Object>> iterator = typeAdapters.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Type, Object> entry = iterator.next();
                addTypeAdapter(builder, entry.getKey(), entry.getValue());
            }
        }
        builder.setLenient();
        return builder.create();
    }
}
