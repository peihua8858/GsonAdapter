package com.fz.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * 为了gson 能够正常解析出服务器返回数据 做容错处理。
 */
public class GsonFactory {

    public static class Factory {
        private final GsonBuilder mBuilder = new GsonBuilder();
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

        public static ReflectiveTypeAdapterFactory getCollectionsFactory() {
            final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(
                    new ConstructorConstructor(Collections.emptyMap(), DEFAULT_USE_JDK_UNSAFE, Collections.emptyList()));
            return new ReflectiveTypeAdapterFactory(
                    new ConstructorConstructor(Collections.emptyMap(), DEFAULT_USE_JDK_UNSAFE, Collections.emptyList()),
                    FieldNamingPolicy.IDENTITY,
                    Excluder.DEFAULT,
                    jsonAdapterFactory);
        }

        public static CollectionTypeAdapterFactory getMapFactory() {
            return new CollectionTypeAdapterFactory(new ConstructorConstructor(Collections.emptyMap(), DEFAULT_USE_JDK_UNSAFE, Collections.emptyList()));
        }

        public static MapTypeAdapterFactory getMapFactory2() {
            return new MapTypeAdapterFactory(new ConstructorConstructor(Collections.emptyMap(), DEFAULT_USE_JDK_UNSAFE, Collections.emptyList()), false);
        }

        public Factory(boolean isAllAdapter) {
            if (isAllAdapter) {
                addAllAdapters();
            }
        }

        public Factory addAllAdapters() {
            //注入 type adapter
            // 集合
            //Object
            mBuilder.registerTypeAdapterFactory(getCollectionsFactory());
            mBuilder.registerTypeAdapterFactory(getMapFactory());
            mBuilder.registerTypeAdapterFactory(getMapFactory2());

            //注入 8大基本类型 type adapter
            mBuilder.registerTypeAdapter(Double.class, DOUBLE);
            mBuilder.registerTypeAdapter(Boolean.class, BOOLEAN);
            mBuilder.registerTypeAdapter(Float.class, FLOAT);
            mBuilder.registerTypeAdapter(Integer.class, INTEGER);
            mBuilder.registerTypeAdapter(Long.class, LONG);
            mBuilder.registerTypeAdapter(int.class, INTEGER);
            mBuilder.registerTypeAdapter(float.class, FLOAT);
            mBuilder.registerTypeAdapter(double.class, DOUBLE);
            mBuilder.registerTypeAdapter(boolean.class, BOOLEAN);
            mBuilder.registerTypeAdapter(long.class, LONG);
            mBuilder.registerTypeAdapter(String.class, STRING);
            mBuilder.serializeNulls();
            return this;
        }

        public Factory setLenient() {
            mBuilder.setLenient();
            return this;
        }

        public Factory setFieldNamingPolicy(FieldNamingPolicy fieldNamingPolicy) {
            mBuilder.setFieldNamingPolicy(fieldNamingPolicy);
            return this;
        }

        public Factory registerTypeAdapter(Type type, Object typeAdapter) {
            mBuilder.registerTypeAdapter(type, typeAdapter);
            return this;
        }

        public Factory setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
            mBuilder.setFieldNamingStrategy(fieldNamingStrategy);
            return this;
        }

        public Factory setTypeAdapterFactory(TypeAdapterFactory typeAdapterFactory) {
            mBuilder.registerTypeAdapterFactory(typeAdapterFactory);
            return this;
        }

        public Factory registerTypeAdapterFactory(TypeAdapterFactory factory) {
            mBuilder.registerTypeAdapterFactory(factory);
            return this;
        }

        public GsonBuilder getBuilder() {
            return mBuilder;
        }

        public Gson build() {
            return mBuilder.create();
        }
    }

    public static Factory createFactory() {
        return new Factory(true);
    }

    public static Factory createFactory(boolean isAllAdapter) {
        return new Factory(isAllAdapter);
    }
    public static Gson createGson() {
        return createFactory().setLenient().build();
    }
}
