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
 * 特征采集事件
 */
data class CaptureDataProcessEvent(
    var ipAddress: String? = null,
    var ipv6Address: String? = null,
    var portNo: Int? = null,
    var protocol: String? = null,
    var macAddress: String? = null,
    var channelID: Int? = null,
    var dateTime: String? = null,
    var activePostCount: Int? = null,
    var eventType: String? = null,
    var eventState: String? = null,
    var eventDescription: String? = null,
    var deviceID: String? = null,
    var CaptureDataProcessEvent: CaptureDataProcessEventBean? = null
)

data class CaptureDataProcessEventBean(
    val type: String? = null,
    val result: String? = null,
    val enrollTimes: Int = 0,
)

/**
 * 采集类型
 */
enum class CaptureType {
    fingerPrint,    //指纹
    face,           //人脸
    card            //卡片
}

/**
 * 采集结果类型
 */
enum class CaptureResultType {
    start,          //采集开始
    processing,     //采集中
    success,        //采集成功
    failed          //采集失败
}
