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
package com.hik.proto.event

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.hik.proto.base.ApiInit.ApiUploadListener
import com.hik.proto.base.ApiInit.registerApi
import com.hik.proto.base.ApiInit.unregisterApi
import com.hik.proto.data.event.*
import com.hik.proto.data.param.UploadEventType
import com.hik.proto.utils.json.JsonUtil
import com.hik.proto.utils.json.JsonUtil.getValue

/**
 * Created by linzijian on 2021/2/22.
 */
class ApiListener : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun startListener() {
        registerApi(object : ApiUploadListener {
            override fun onUpload(body: String) {
                Log.d("onUpload", "body=$body")
                val str = getValue(body, "eventType")
                when {
                    UploadEventType.AccessControllerEvent.name == str -> {
                        //门禁控制事件
                        ApiEvent.setAccessControllerEvent(
                            JsonUtil.toJsonBean(
                                body,
                                AccessControllerEvent::class.java
                            )
                        )
                    }
                    UploadEventType.voiceTalkEvent.name == str -> {
                        //通话对讲事件
                        ApiEvent.setVoiceTalkEvent(
                            JsonUtil.toJsonBean(
                                body,
                                VoiceTalkEvent::class.java
                            )
                        )
                    }
                    UploadEventType.OperationNotificationEvent.name == str -> {
                        //操作通知事件
                        ApiEvent.setOperationNotificationEvent(
                            JsonUtil.toJsonBean(
                                body,
                                OperationNotificationEvent::class.java
                            )
                        )
                    }
                    UploadEventType.CaptureDataProcessEvent.name == str -> {
                        //特征采集事件
                        ApiEvent.setCaptureDataProcessEvent(
                            JsonUtil.toJsonBean(
                                body,
                                CaptureDataProcessEvent::class.java
                            )
                        )
                    }
                    UploadEventType.ACSStatusChangeEvent.name == str -> {
                        //状态变化事件
                        ApiEvent.setACSStatusChangeEventBean(
                            JsonUtil.toJsonBean(
                                body,
                                ACSStatusChangeEventBean::class.java
                            )
                        )
                    }
                    UploadEventType.KeyEvent.name == str -> {
                        //按键操作事件
                        ApiEvent.setKeyEventBean(
                            JsonUtil.toJsonBean(
                                body,
                                KeyEventBean::class.java
                            )
                        )
                    }
                    UploadEventType.AdminVerifyResultEvent.name == str -> {
                        //管理员认证事件
                        ApiEvent.setAdminVerifyResultEvent(
                            JsonUtil.toJsonBean(
                                body,
                                AdminVerifyResultEvent::class.java
                            )
                        )
                    }
                }
            }
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun stopListener() {
        unregisterApi()
    }
}