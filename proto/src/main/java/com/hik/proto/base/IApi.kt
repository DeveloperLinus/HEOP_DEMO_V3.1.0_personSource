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

/**
 * Created by linzijian on 2021/2/21.
 */
interface IApi {
    var protocolType: ProtocolType  // 协议类型
    var requestType: RequestType    // 请求类型
    var protocol: String            // 协议
}

// 协议类型
enum class ProtocolType {
    XML,
    JSON
}

// 请求类型
enum class RequestType {
    GET,
    POST,
    PUT,
    DELETE
}
