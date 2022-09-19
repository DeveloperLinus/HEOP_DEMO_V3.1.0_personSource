/*
 * Copyright 2020-present hikvision
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hik.proto.utils.json

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser

/**
 * Created by linzijian on 2021/2/21.
 */
object JsonUtil {
    private const val TAG = "JsonUtil"

    /**
     * 转成json
     */
    fun toJsonString(obj: Any?): String? {
        if (obj is String) {
            return obj
        }
        try {
            return Gson().toJson(obj)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "toJsonString catch: \n${e.message}")
        }
        return null
    }

    /**
     * 转成JsonBean
     */
    fun <T> toJsonBean(json: String, cls: Class<T>?): T? {
        var t: T? = null
        try {
            /*Log.e(TAG, "=== : \n$json")*/
            t = Gson().fromJson(json, cls)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "toJsonBean catch: \n$json")
        }
        return t
    }

    @JvmStatic
    fun getValue(json: String, key: String): String {
        var eventType: String = ""
        try {
            eventType = JsonParser.parseString(json).asJsonObject[key].asString
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e(TAG, "getValue: json=$json")
        }
        return eventType
    }
}
