package com.fz.gsonadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.fz.gson.GsonFactory
import com.fz.gson.StringTypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun onClick(view: View) {
        val type = object : TypeToken<Response>() {}.type
        try {
            val json = "{\n" +
                    "\t\"buyer_protection_items\": [\n" +
                    "\t\tnull,\n" +
                    "\t\t\"30 Jours de Garantie Retour\",\n" +
                    "\t\t\"Garantie de prix\"\n" +
                    "\t],\n" +
                    "\t\"buyerTitle\":null,\n" +
                    "\t\"buyerContent\":{\n" +
                    "\t\t\"a\":null,\n" +
                    "\t\t\"b\":\"5566\"\n" +
                    "\t},\n" +
                    "\t\"C\":null,\n" +
                    "\t\"D\":null,\n" +
                    "\t\"E\":null,\n" +
                    "\t\"F\":null,\n" +
                    "\t\"G\":null,\n" +
                    "\t\"H\":null\n" +
                    "}"
            val response: Response = GsonFactory.create().fromJson(json, type)
            Log.d("sss", "response>>>$response")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    data class Response(
        val buyer_protection_items: MutableList<String>,
        val buyerTitle: String,
        val buyerContent: Content,
        val C:Int,
        val D:Float,
        val E:Double,
        val F:Long,
        val G:Short,
        val H:Boolean,
    )

    data class Content(
        val a: String = "", val b: String = "")
}