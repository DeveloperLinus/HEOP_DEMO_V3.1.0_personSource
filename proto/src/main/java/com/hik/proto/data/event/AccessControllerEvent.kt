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
 * 门禁控制事件
 */
data class AccessControllerEvent(
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
    var AccessControllerEvent: AccessControllerEventBean? = null,
    var URLCertificationType: String? = null
)

data class AccessControllerEventBean(
    var deviceName: String? = null,
    //主类型
    var majorEventType: Int? = null,
    //子类型
    var subEventType: Int? = null,
    var inductiveEventType: String? = null,
    var netUser: String? = null,
    var remoteHostAddr: String? = null,
    var cardNo: String? = null,
    var cardType: Int? = null,
    var name: String? = null,
    var whiteListNo: Int? = null,
    var reportChannel: Int? = null,
    var cardReaderKind: Int? = null,
    var cardReaderNo: Int? = null,
    var doorNo: Int? = null,
    var verifyNo: Int? = null,
    var alarmInNo: Int? = null,
    var alarmOutNo: Int? = null,
    var caseSensorNo: Int? = null,
    var RS485No: Int? = null,
    var multiCardGroupNo: Int? = null,
    var accessChannel: Int? = null,
    var deviceNo: Int? = null,
    var distractControlNo: Int? = null,
    var employeeNo: Int? = null,
    var employeeNoString: String? = null,
    var employeeName: Int? = null,
    var localControllerID: Int? = null,
    var InternetAccess: Int? = null,
    var type: Int? = null,
    var MACAddr: String? = null,
    var swipeCardType: Int? = null,
    var serialNo: Int? = null,
    var channelControllerID: Int? = null,
    var channelControllerLampID: Int? = null,
    var channelControllerIRAdaptorID: Int? = null,
    var channelControllerIREmitterID: Int? = null,
    //用户类型
    var userType: String? = null,
    var currentVerifyMode: String? = null,
    var currentEvent: Boolean? = null,
    var QRCodeInfo: String? = null,
    var thermometryUnit: String? = null,
    var currTemperature: Float? = null,
    var isAbnomalTemperature: Boolean? = null,
    var RegionCoordinates: RegionCoordinatesBean? = null,
    var remoteCheck: Boolean? = null,
    var mask: String? = null,
    var frontSerialNo: Int? = null,
    var attendanceStatus: String? = null,
    var statusValue: Int? = null,
    var pictureURL: String? = null,
    var visibleLightURL: String? = null,
    var thermalURL: String? = null,
    var picturesNumber: Int? = null,
    var unlockType: String? = null,
    var classroomId: String? = null,
    var classroomName: String? = null,
    var analysisModule: String? = null,
    var customInfo: String? = null

)

data class RegionCoordinatesBean(
    var positionX: Int? = null,
    var positionY: Int? = null
)

enum class EnumUserType {
    normal,         //主人
    visitor,        //访客
    blackList,      //黑名单
    administrators  //管理员
}