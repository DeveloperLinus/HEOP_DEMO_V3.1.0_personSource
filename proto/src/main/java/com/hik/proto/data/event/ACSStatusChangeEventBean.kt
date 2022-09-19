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
package com.hik.proto.data.event

/**
 * Created by linzijian on 2021/3/11.
 * 状态变化事件
 */
data class ACSStatusChangeEventBean(
    var ipAddress: String? = null,
    var ipv6Address: String? = null,
    var portNo: String? = null,
    var protocol: String? = null,
    var macAddress: String? = null,
    var channelID: Int? = 0,
    var dateTime: String? = null,
    var activePostCount: Int? = 0,
    var eventType: String? = null,
    var eventState: String? = null,
    var eventDescription: String? = null,
    var ACSStatusChangeEvent: ACSStatusChangeEvent? = null
)

data class ACSStatusChangeEvent(
    var netStatus: String? = null,             //网络状态
    var signalStatus: String? = null,          //信号状态
    var sipStatus: String? = null,             //sip状态
    var workMode: String? = null,              //认证工作模式
    var deployStatus: String? = null,          //布防状态
    var ezvizStatus: String? = null,           //萤石状态
    var voipStatus: String? = null,            //voip状态
    var verifyMode: String? = null             //验证模式
)
