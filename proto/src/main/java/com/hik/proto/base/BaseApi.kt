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
package com.hik.proto.base

import android.util.Log
import com.hik.proto.utils.thread.ThreadPoolProxyFactory
import com.hik.proto.utils.ApiUtil
import com.hik.vis.libipc_unix.SocketJna
import java.util.concurrent.Future

/**
 * Created by linzijian on 2021/2/21.
 */
class BaseApi<T> private constructor(
    override var protocolType: ProtocolType,    // 协议类型
    override var requestType: RequestType,      // 请求类型
    override var protocol: String               // 协议
) : IApi {
    companion object {
        val TAG = "BaseApi"

        @JvmStatic
        fun <T> putXml(protocol: String) = BaseApi<T>(
            ProtocolType.XML,
            RequestType.PUT,
            protocol
        )

        @JvmStatic
        fun <T> putJson(protocol: String) = BaseApi<T>(
            ProtocolType.JSON,
            RequestType.PUT,
            protocol
        )

        @JvmStatic
        fun <T> postXml(protocol: String) = BaseApi<T>(
            ProtocolType.XML,
            RequestType.POST,
            protocol
        )

        @JvmStatic
        fun <T> postJson(protocol: String) = BaseApi<T>(
            ProtocolType.JSON,
            RequestType.POST,
            protocol
        )

        @JvmStatic
        fun <T> getXml(protocol: String) = BaseApi<T>(
            ProtocolType.XML,
            RequestType.GET,
            protocol
        )

        @JvmStatic
        fun <T> getJson(protocol: String) = BaseApi<T>(
            ProtocolType.JSON,
            RequestType.GET,
            protocol
        )

        @JvmStatic
        fun <T> deleteXml(protocol: String) = BaseApi<T>(
            ProtocolType.XML,
            RequestType.DELETE,
            protocol
        )

        @JvmStatic
        fun <T> deleteJson(protocol: String) = BaseApi<T>(
            ProtocolType.JSON,
            RequestType.DELETE,
            protocol
        )
    }

    /**
     * 发送请求，同步获取请求结果
     */
    @JvmOverloads
    fun sync(
        requestBean: Any? = null,
        resultBeanType: Class<T>,
        timeout: Int = ApiInit.DEFAULT_TIMEOUT,
        respBuffSize: Int = ApiInit.DEFAULT_BUFF_SIZE
    ): Any? {
        val url = ApiUtil.getUrl(requestType, protocol)
        val reqBody = ApiUtil.getRequestBody(protocolType, requestBean)
        // 发送请求
        Log.d(TAG, "url=$url, request=${SocketJna.getInstance().filterSensitiveData(reqBody)}")
        val resp: String? = SocketJna.getInstance().request(url, reqBody, timeout, respBuffSize)
        Log.d(TAG, "url=$url, response=${SocketJna.getInstance().filterSensitiveData(resp ?: "null")}")
        // 应答转bean
        resp?.let {
            return ApiUtil.getResultBean(protocolType, resp, resultBeanType) ?: run {
                Log.e(TAG, "url=$url, error getResultBean")
            }
        } ?: run {
            Log.e(TAG, "url=$url, error response=null")
            return null
        }
    }

    /**
     * 发送请求，异步获取请求结果
     */
    @JvmOverloads
    fun async(
        requestBean: Any? = null,
        resultBeanType: Class<T>,
        apiCall: ApiCall<T>? = null,
        timeout: Int = ApiInit.DEFAULT_TIMEOUT,
        respBuffSize: Int = ApiInit.DEFAULT_BUFF_SIZE
    ): Future<*>? {
        return ThreadPoolProxyFactory.defaultThreadPool?.submit(
            mRun(
                protocolType,
                requestType,
                protocol,
                requestBean,
                resultBeanType,
                apiCall,
                timeout,
                respBuffSize
            )
        )
    }

    // 静态内部类,防止内存泄漏
    private class mRun<T>(
        val protocolType: ProtocolType,
        val requestType: RequestType,
        val protocol: String,
        val requestBean: Any?,
        val resultBeanType: Class<T>,
        val apiCall: ApiCall<T>?,
        val timeout: Int,
        val respBuffSize: Int
    ) : Runnable {

        override fun run() {
            val url = ApiUtil.getUrl(requestType, protocol)
            val reqBody = ApiUtil.getRequestBody(protocolType, requestBean)
            // 发送请求
            Log.d(TAG, "url=$url, request=${SocketJna.getInstance().filterSensitiveData(reqBody)}")
            val resp: String? = SocketJna.getInstance().request(url, reqBody, timeout, respBuffSize)
            Log.d(
                TAG,
                "url=$url, response=${SocketJna.getInstance().filterSensitiveData(resp ?: "null")}"
            )
            resp?.let {
                ApiUtil.getResultBean(protocolType, resp, resultBeanType)?.let { result ->
                    apiCall?.onSuccess(result)
                } ?: run {
                    Log.e(TAG, "url=$url, error getResultBean")
                    apiCall?.onError()
                }
            } ?: run {
                Log.e(TAG, "url=$url, error response=null")
                apiCall?.onError()
            }
        }
    }
}

/**
 * 发送请求，异步获取请求结果
 */
inline fun <reified T> BaseApi<T>.async(
    apiCall: ApiCall<T>? = null,
    requestBean: Any? = null,
    timeout: Int = ApiInit.DEFAULT_TIMEOUT,
    respBuffSize: Int = ApiInit.DEFAULT_BUFF_SIZE
): Future<*>? {
    return async(requestBean, T::class.java, apiCall, timeout, respBuffSize)
}

/**
 * 发送请求，同步获取请求结果
 */
inline fun <reified T> BaseApi<T>.sync(
    requestBean: Any? = null,
    timeout: Int = ApiInit.DEFAULT_TIMEOUT,
    respBuffSize: Int = ApiInit.DEFAULT_BUFF_SIZE
): Any? {
    return sync(requestBean, T::class.java, timeout, respBuffSize)
}
