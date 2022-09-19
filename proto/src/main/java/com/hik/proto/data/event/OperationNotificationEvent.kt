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
 * 操作通知事件
 */
data class OperationNotificationEvent(
    var ipAddress: String? = null,
    var ipv6Address: String? = null,
    var portNo: String? = null,
    var protocol: String? = null,
    var macAddress: String? = null,
    var eventType: String? = null,
    var eventState: String? = null,
    var eventDescription: String? = null,
    var OperationNotificationEvent: OperationNotificationEventBean? = null
)

data class OperationNotificationEventBean(
    var operation: String? = null,
    var upgradePackagePath: String? = null,
    var packageName: String? = null,
    var parameters: String? = null
)

enum class OperationType {
    installApp,     //安装
    uninstallApp,   //卸载
    clearApp,       //清除数据
    startApp,       //启动
    stopApp,        //停止
    upgrade,        //OTA升级
    publishUpdate,  //信息发布内容更新
    hicoreOnline,   //底层服务上线
    restore,        //恢复默认参数
    message,        //留言
    messageTimeout, //留言超时
    MotionDetected, //移动侦测生效
    upgrading,      //增量升级
    appUpdate       //有版本更新
}
