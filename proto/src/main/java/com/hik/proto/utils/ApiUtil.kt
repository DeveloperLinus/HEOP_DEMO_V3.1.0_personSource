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
package com.hik.proto.utils

import com.hik.proto.base.ProtocolType
import com.hik.proto.base.RequestType
import com.hik.proto.utils.json.JsonUtil
import com.hik.proto.utils.xml.XStreamUtil

/**
 * Created by linzijian on 2021/2/21.
 */
object ApiUtil {
    val TAG = "ApiUtil"

    fun getUrl(requestType: RequestType, protocol: String): String = requestType.name + protocol

    fun getRequestBody(protocolType: ProtocolType, requestBean: Any?): String =
        requestBean?.let {
            when (protocolType) {
                ProtocolType.XML -> XStreamUtil.toXmlString(requestBean)
                ProtocolType.JSON -> JsonUtil.toJsonString(requestBean)
            }
        } ?: ""

    fun <T> getResultBean(protocolType: ProtocolType, resp: String, resultBeanType: Class<T>): T? =
        when (protocolType) {
            ProtocolType.XML -> XStreamUtil.toXmlBean(resp, resultBeanType)
            ProtocolType.JSON -> JsonUtil.toJsonBean(resp, resultBeanType)
        }
}
