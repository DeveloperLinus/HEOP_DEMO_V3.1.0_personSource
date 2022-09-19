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
package com.hik.proto

import com.hik.proto.base.BaseApi
import com.hik.proto.data.bean.*
import com.hik.proto.data.bean.ethernet.*
import com.hik.proto.data.bean.user.UserData
import com.hik.proto.data.bean.user.card.CaptureCardData
import com.hik.proto.data.bean.user.card.CardData
import com.hik.proto.data.bean.user.face.CaptureFaceData
import com.hik.proto.data.bean.user.fp.CaptureFingerPrint
import com.hik.proto.data.bean.user.fp.FpData
import com.hik.proto.data.bean.user.user_search.UserDataSearch

/**
 * Created by linzijian on 2021/2/21.
 */
object Apis {
    // 获取激活状态
    val getActivateStatus = BaseApi.getXml<ActivateStatus>("/SDK/activateStatus")

    // 激活设备
    val activateDevice = BaseApi.putJson<ResponseStatus>("/ISAPI/HEOP/System/activate?format=json")

    // 获取有线网络配置
    val getEthernet = BaseApi.getXml<IPAddress>("/ISAPI/System/Network/interfaces/1/ipAddress")

    // 设置有线网络
    val setEthernet = BaseApi.putXml<ResponseStatus>("/ISAPI/System/Network/interfaces/1/ipAddress")

    // 设置认证模式
    val setVeriyMode = BaseApi.putJson<ResponseStatus>("/ISAPI/HEOP/AccessControl/verifyMode?format=json")

    // 远程开门
    val remoteControlDoor = BaseApi.putXml<ResponseStatus>("/ISAPI/AccessControl/RemoteControl/door/65535")

    // 人员管理相关
    // 获取用户数据信息,包括：@姓名 + @工号 + @是否录入人脸 + @是否录入指纹
    val getUserData = BaseApi.getJson<UserData>("/ISAPI/AccessControl/UserInfo/UserList?format=json")
    // 查询用户
    val getUserInfo = BaseApi.postJson<UserDataSearch>("/ISAPI/AccessControl/UserInfo/Search?format=json")
    // 添加用户
    val addUser = BaseApi.putJson<ResponseStatus>("/ISAPI/AccessControl/UserInfo/SetUp?format=json")
    // 删除用户
    val delUser = BaseApi.putJson<ResponseStatus>("/ISAPI/AccessControl/UserInfo/Delete?format=json")

    // 指纹相关
    // 获取指纹数据
    val getFpData = BaseApi.postJson<FpData>("/ISAPI/AccessControl/FingerPrintUpload?format=json")
    // 获取卡片数据
    val getCardData = BaseApi.postJson<CardData>("/ISAPI/AccessControl/CardInfo/Search?format=json")

    // 采集相关
    // 采集预处理
    @JvmField
    val setWindowMode = BaseApi.postJson<ResponseStatus>("/ISAPI/UI/authMode?format=json")
    // 指纹采集
    val fpCapture = BaseApi.postXml<CaptureFingerPrint>("/ISAPI/AccessControl/CaptureFingerPrint")
    // 人脸采集
    val faceCapture = BaseApi.postXml<CaptureFaceData>("/ISAPI/AccessControl/CaptureFaceData")
    // 卡片采集
    @JvmField
    val cardCapture = BaseApi.getJson<CaptureCardData>("/ISAPI/AccessControl/CaptureCardInfo?format=json")
}
