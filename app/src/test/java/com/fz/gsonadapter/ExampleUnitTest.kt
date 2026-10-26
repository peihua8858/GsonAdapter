package com.fz.gsonadapter

import com.fz.gson.GsonFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun listFieldFallsBackToEmptyListWhenJsonIsObject() {
        val json = """
            {
              "buyer_protection_items": [null, "30 Jours de Garantie Retour", "Garantie de prix"],
              "buyerTitle": null,
              "buyerContent": {"a": null, "b": "5566"},
              "status": 200,
              "C": null,
              "D": null,
              "E": null,
              "F": null,
              "G": null,
              "H": null,
              "cc": {"a": "1", "b": "2"}
            }
        """.trimIndent()

        val response = GsonFactory.createGson().fromJson(json, MainActivity.Response::class.java)

        assertNotNull(response)
        assertNotNull(response.cc)
        assertTrue(response.cc!!.isEmpty())
        assertEquals(200, response.status)
        assertEquals("", response.buyerTitle)
        assertEquals("", response.buyerContent.a)
        assertEquals("5566", response.buyerContent.b)
        assertFalse(response.H)
    }
}
