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

import com.hik.proto.callback.ProtectedUnPeekLiveData
import com.hik.proto.callback.UnPeekLiveData
import com.hik.proto.data.event.*

/**
 * Created by linzijian on 2021/3/11.
 */
object ApiEvent {

    /**
     * 门禁控制事件
     */
    private val AccessControllerEvent = UnPeekLiveData<AccessControllerEvent>()

    fun getAccessControllerEvent(): ProtectedUnPeekLiveData<AccessControllerEvent> {
        return AccessControllerEvent
    }

    fun setAccessControllerEvent(e: AccessControllerEvent?) {
        AccessControllerEvent.postValue(e)
    }

    /**
     * 通话对讲事件
     */
    private val VoiceTalkEvent = UnPeekLiveData<VoiceTalkEvent>()

    fun getVoiceTalkEvent(): ProtectedUnPeekLiveData<VoiceTalkEvent> {
        return VoiceTalkEvent
    }

    fun setVoiceTalkEvent(e: VoiceTalkEvent?) {
        VoiceTalkEvent.postValue(e)
    }

    /**
     * 操作通知事件
     */
    private val OperationNotificationEvent = UnPeekLiveData<OperationNotificationEvent>()

    fun getOperationNotificationEvent(): ProtectedUnPeekLiveData<OperationNotificationEvent> {
        return OperationNotificationEvent
    }

    fun setOperationNotificationEvent(e: OperationNotificationEvent?) {
        OperationNotificationEvent.postValue(e)
    }

    /**
     * 特征采集事件
     */
    private val CaptureDataProcessEvent = UnPeekLiveData<CaptureDataProcessEvent>()

    fun getCaptureDataProcessEvent(): ProtectedUnPeekLiveData<CaptureDataProcessEvent> {
        return CaptureDataProcessEvent
    }

    fun setCaptureDataProcessEvent(e: CaptureDataProcessEvent?) {
        CaptureDataProcessEvent.postValue(e)
    }

    /**
     * 状态变化事件
     */
    private val ACSStatusChangeEventBean = UnPeekLiveData<ACSStatusChangeEventBean>()

    fun getACSStatusChangeEventBean(): ProtectedUnPeekLiveData<ACSStatusChangeEventBean> {
        return ACSStatusChangeEventBean
    }

    fun setACSStatusChangeEventBean(e: ACSStatusChangeEventBean?) {
        ACSStatusChangeEventBean.postValue(e)
    }

    /**
     * 按键操作事件
     */
    private val KeyEventBean = UnPeekLiveData<KeyEventBean>()

    fun getKeyEventBean(): ProtectedUnPeekLiveData<KeyEventBean> {
        return KeyEventBean
    }

    fun setKeyEventBean(e: KeyEventBean?) {
        KeyEventBean.postValue(e)
    }

    /**
     * 管理员认证事件
     */
    private val AdminVerifyResultEvent = UnPeekLiveData<AdminVerifyResultEvent>()

    fun getAdminVerifyResultEvent(): ProtectedUnPeekLiveData<AdminVerifyResultEvent> {
        return AdminVerifyResultEvent
    }

    fun setAdminVerifyResultEvent(e: AdminVerifyResultEvent?) {
        AdminVerifyResultEvent.postValue(e)
    }
}