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
package com.hik.proto.data.param

/**
 * Created by linzijian on 2021/3/5.
 * 上报的事件类型
 */
enum class UploadEventType {
    AccessControllerEvent,       //门禁事件上传，用于上报认证结果
    voiceTalkEvent,              //对讲交互事件，用于上报通话状态
    ACSStatusChangeEvent,        //状态变化事件，用于上报状态变化
    OperationNotificationEvent,  //通知上报事件
    AdminVerifyResultEvent,      //管理员认证
    CaptureDataProcessEvent,     //采集事件上报
    KeyEvent                     //按键事件
}
