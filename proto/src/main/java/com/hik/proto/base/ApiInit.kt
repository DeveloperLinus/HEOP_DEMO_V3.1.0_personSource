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

import com.hik.vis.libipc_unix.Libipc_unixSoLibrary
import com.hik.vis.libipc_unix.SocketJna

/**
 * Created by linzijian on 2021/2/21.
 */
object ApiInit {
    private val serviceTaskCallBack = Libipc_unixSoLibrary.p_service_task_f { 0 }

    // 超时时间
    const val DEFAULT_TIMEOUT = 5000

    // 缓存大小
    const val DEFAULT_BUFF_SIZE = 10 * 1024
    private var listener: ApiUploadListener? = null

    // 上报监听
    interface ApiUploadListener {
        fun onUpload(body: String)
    }

    @JvmStatic
    fun initSocketJna(timeOut: Int? = null, buffSize: Int? = null) {

        SocketJna.getInstance().init(
            Libipc_unixSoLibrary.IPC_FILE,
            Libipc_unixSoLibrary.IPC_HIK_APPID_UI_SERVICE,
            Libipc_unixSoLibrary.HEOP_PROCESS_SERVER_E.IPC_HIK_MAIN_SERVICE,
            serviceTaskCallBack,
            timeOut ?: DEFAULT_TIMEOUT,
            buffSize ?: DEFAULT_BUFF_SIZE
        )
    }

    @JvmStatic
    fun registerApi(listener: ApiUploadListener) {
        ApiInit.listener = listener
        SocketJna.getInstance().registerSubscriber("", msgSubscriber)
    }

    @JvmStatic
    fun unregisterApi() {
        SocketJna.getInstance().unregisterSubscriber("")
    }

    private val msgSubscriber = Libipc_unixSoLibrary.p_sub_f { pstSub ->
        val body = pstSub.pbyBody
        listener?.onUpload(body)
        0
    }
}
