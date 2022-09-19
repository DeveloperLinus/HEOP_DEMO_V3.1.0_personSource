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
 * 管理员认证事件
 */
data class AdminVerifyResultEvent(
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
    var AdminVerifyResultEvent: AdminVerifyResultEventBean? = null
)

data class AdminVerifyResultEventBean(
    var result: String? = null,
    var adminName: String? = null
)

enum class AdminVerifyResultType {
    success,        //管理员认证成功
    failed          //管理员认证失败
}
