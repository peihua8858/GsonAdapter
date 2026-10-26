package com.fz.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ExampleUnitTest {
    private final Gson gson = GsonFactory.createGson();

    @Test
    public void mismatchedContainersDoNotInterruptFollowingFields() {
        Model model = gson.fromJson("{\"list\":{},\"map\":[1,2],\"array\":\"bad\",\"tail\":\"ok\"}", Model.class);

        assertTrue(model.list.isEmpty());
        assertNull(model.map);
        assertNull(model.array);
        assertEquals("ok", model.tail);
    }

    @Test
    public void booleanAcceptsStringsAndNumbers() {
        BooleanModel model = gson.fromJson(
                "{\"trueString\":\"true\",\"falseString\":\"false\",\"one\":1,\"zero\":0}",
                BooleanModel.class);

        assertTrue(model.trueString);
        assertFalse(model.falseString);
        assertTrue(model.one);
        assertFalse(model.zero);
    }

    @Test
    public void stringPreservesObjectAndArrayJson() {
        StringModel model = gson.fromJson(
                "{\"object\":{\"a\":1},\"array\":[1,2],\"empty\":null}",
                StringModel.class);

        assertEquals("{\"a\":1}", model.object);
        assertEquals("[1,2]", model.array);
        assertEquals("", model.empty);
    }

    @Test
    public void numberMismatchFallsBackWithoutOverflow() {
        NumberModel model = gson.fromJson(
                "{\"integer\":{},\"decimal\":\"1.5\",\"overflow\":9999999999,\"boxed\":null}",
                NumberModel.class);

        assertEquals(0, model.integer);
        assertEquals(1.5d, model.decimal, 0d);
        assertEquals(0, model.overflow);
        assertEquals(Integer.valueOf(0), model.boxed);
    }

    @Test
    public void collectionPreservesNullElements() {
        Model model = gson.fromJson("{\"list\":[null,\"a\"]}", Model.class);

        assertEquals(Arrays.asList(null, "a"), model.list);
    }

    @Test
    public void arraysAndEnumsUseNativeAdapters() {
        NativeTypes model = gson.fromJson(
                "{\"array\":[\"a\",\"b\"],\"day\":\"MON\",\"number\":5}",
                NativeTypes.class);

        assertEquals(Arrays.asList("a", "b"), Arrays.asList(model.array));
        assertEquals(Day.MON, model.day);
        assertEquals(5, model.number.intValue());
    }

    @Test
    public void serializationMatchesNativeGson() {
        NativeTypes model = new NativeTypes();
        model.array = new String[]{"a", "b"};
        model.day = Day.MON;
        model.number = new BigDecimal("1.25");
        model.date = new Date(1700000000000L);
        model.id = UUID.fromString("11111111-2222-3333-4444-555555555555");

        String actual = gson.toJson(model);
        String expected = new Gson().toJson(model);

        assertEquals(JsonParser.parseString(expected), JsonParser.parseString(actual));
    }

    @Test
    public void fieldNamingPolicyAppliesToBothDirections() {
        Gson snakeCase = GsonFactory.createFactory()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .build();

        NamingModel model = snakeCase.fromJson("{\"user_name\":\"Ada\"}", NamingModel.class);

        assertEquals("Ada", model.userName);
        assertEquals("{\"user_name\":\"Ada\"}", snakeCase.toJson(model));
    }

    static class Model {
        List<String> list;
        Map<String, String> map;
        String[] array;
        String tail;
    }

    static class BooleanModel {
        boolean trueString;
        boolean falseString;
        boolean one;
        boolean zero;
    }

    static class StringModel {
        String object;
        String array;
        String empty;
    }

    static class NumberModel {
        int integer;
        double decimal;
        int overflow;
        Integer boxed;
    }

    static class NativeTypes {
        String[] array;
        Day day;
        Number number;
        Date date;
        UUID id;
    }

    static class NamingModel {
        String userName;
    }

    enum Day {
        MON
    }
}
