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

import com.thoughtworks.xstream.converters.reflection.FieldKey
import com.thoughtworks.xstream.converters.reflection.FieldKeySorter
import java.lang.reflect.Field
import java.util.*

/**
 * Created by linzijian on 2021/2/21.
 */
@SuppressWarnings("unchecked")
class PartialSeqSorter : FieldKeySorter {
    override fun sort(type: Class<*>, byFieldKey: Map<*, *>): Map<*, *> {
        val sequence: Annotation? = type.getAnnotation(XMLSequence::class.java)
        if (sequence != null) {
            val fieldsOrder: Array<String> = (sequence as XMLSequence).value as Array<String>
            val custom: MutableMap<FieldKey, Field> = LinkedHashMap()
            val notCustom: MutableMap<FieldKey, Field> = LinkedHashMap()
            val fields: Set<Map.Entry<FieldKey, Field>> = byFieldKey.entries as Set<Map.Entry<FieldKey, Field>>
            for (fieldName in fieldsOrder) {
                for ((key, value) in fields) {
                    if (fieldName == key.fieldName) {
                        custom[key] = value
                    } else {
                        notCustom[key] = value
                    }
                }
            }
            custom.putAll(notCustom)
            return custom
        } else {
            return byFieldKey
        }
    }
}
