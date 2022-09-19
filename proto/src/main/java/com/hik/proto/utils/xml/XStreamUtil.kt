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
package com.hik.proto.utils.xml

import android.util.Log
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.converters.reflection.FieldDictionary
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider
import com.thoughtworks.xstream.io.xml.Xpp3DomDriver

/**
 * Created by linzijian on 2021/2/21.
 */
@SuppressWarnings("unchecked")
object XStreamUtil {
    private const val TAG = "XStreamUtil"

    /**
     * 转成xml
     */
    fun toXmlString(obj: Any?): String? {
        if (obj is String) {
            return obj
        }
        try {
            val xStream = XStream(PureJavaReflectionProvider(FieldDictionary(PartialSeqSorter())))
            if (obj != null) {
                xStream.processAnnotations(obj.javaClass)
            }
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>${xStream.toXML(obj)}"
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "toXmlString catch: \n${e.message}")
        }
        return null
    }

    /**
     * 转成XmlBean
     */
    fun <T> toXmlBean(xmlStr: String, cls: Class<T>?): T? {
        var t: T? = null
        try {
            val xStream = XStream(Xpp3DomDriver())
            xStream.processAnnotations(cls)
            t = xStream.fromXML(xmlStr) as T?
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "toXmlBean catch: \n$xmlStr")
        }
        return t
    }
}
