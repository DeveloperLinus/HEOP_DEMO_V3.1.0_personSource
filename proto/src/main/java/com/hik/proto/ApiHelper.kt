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

import android.util.Base64
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.hik.proto.base.ApiCall
import com.hik.proto.base.async
import com.hik.proto.base.sync
import com.hik.proto.data.bean.ActivateStatus
import com.hik.proto.data.bean.RemoteControlDoor
import com.hik.proto.data.bean.ResponseStatus
import com.hik.proto.data.bean.ethernet.IPAddress
import com.hik.proto.data.bean.user.authmode.AuthModeType
import com.hik.proto.data.bean.user.authmode.EnrollModeType
import com.hik.proto.data.bean.user.UserDataAdd
import com.hik.proto.data.bean.user.card.CaptureCardData
import com.hik.proto.data.bean.user.face.CaptureFaceDataCond
import com.hik.proto.data.bean.user.face.CaptureFaceData
import com.hik.proto.data.bean.user.fp.CaptureFingerPrint
import com.hik.proto.data.bean.user.fp.CaptureFingerPrintCond
import com.hik.proto.data.param.RemoteControlDoorType
import com.hik.proto.data.param.VerifyModeType
import java.util.concurrent.Future

/**
 * Created by linzijian on 2021/3/4.
 */
object ApiHelper {
    private fun String.toBase64() = String(Base64.encode(this.toByteArray(), 0))

    /**
     * 获取激活状态
     */
    fun getActivateStatus(call: ApiCall<ActivateStatus>): Future<*>? {
        return Apis.getActivateStatus.async(call)
    }

    /**
     * 激活设备
     */
    fun activateDevice(password: String?): Any? {
        val mainObject = JsonObject()
        val subObject = JsonObject()
        if (password != null) {
            subObject.addProperty("password", password.toBase64())
            mainObject.add("ActivateInfo", subObject)
            return Apis.activateDevice.sync(mainObject)
        }
        return null
    }

    /**
     * 获取有线网络配置
     */
    fun getEthernet(call: ApiCall<IPAddress>): Future<*>? {
        return Apis.getEthernet.async(call)
    }

    /**
     * 设置有线网络
     */
    fun setEthernet(ipAddress: IPAddress): Any? {
        return Apis.setEthernet.sync(ipAddress)
    }

    /**
     * 设置认证模式
     */
    fun setVerifyMode(
        call: ApiCall<ResponseStatus>,
        hashMap: HashMap<VerifyModeType, Boolean>
    ): Future<*>? {
        val mainObject = JsonObject()
        val subObject = JsonObject()
        val face = hashMap[VerifyModeType.face]
        val card = hashMap[VerifyModeType.card]
        val fingerPrint = hashMap[VerifyModeType.fingerPrint]
        val QRCode = hashMap[VerifyModeType.QRCode]
        if (face != null) {
            subObject.addProperty(VerifyModeType.face.name, hashMap.get(VerifyModeType.face))
        }
        if (card != null) {
            subObject.addProperty(VerifyModeType.card.name, hashMap.get(VerifyModeType.card))
        }
        if (fingerPrint != null) {
            subObject.addProperty(
                VerifyModeType.fingerPrint.name,
                hashMap.get(VerifyModeType.fingerPrint)
            )
        }
        if (QRCode != null) {
            subObject.addProperty(VerifyModeType.QRCode.name, hashMap.get(VerifyModeType.QRCode))
        }
        mainObject.add("VerifyMode", subObject)
        return Apis.setVeriyMode.async(call, mainObject)
    }

    fun remoteControlDoor(remoteControlDoorType: RemoteControlDoorType, call: ApiCall<ResponseStatus>): Future<*>? {
        return Apis.remoteControlDoor.async(call, RemoteControlDoor(remoteControlDoorType.name))
    }

    /**
     * 获取人员数据信息
     */
    fun getUserData(): Any? {
        val mainObject = JsonObject()
        val subObject = JsonObject()

        subObject.addProperty("startDbID", 0)
        subObject.addProperty("maxCount", 10)
        mainObject.add("UserInfoGetCond", subObject)

        return Apis.getUserData.sync(mainObject)
    }

    /**
     * 查询用户详情
     */
    fun getUserInfo(userId: String): Any? {
        val mainObject = JsonObject()
        val subObject = JsonObject()
        val thirdObject = JsonObject()
        val arrayObject = JsonArray()

        subObject.addProperty("searchID", "100000")
        subObject.addProperty("searchResultPosition", 0)
        subObject.addProperty("maxResults", 3)

        thirdObject.addProperty("employeeNo", userId)
        arrayObject.add(thirdObject)
        subObject.add("EmployeeNoList", arrayObject)

        mainObject.add("UserInfoSearchCond", subObject)

        return Apis.getUserInfo.sync(mainObject)
    }

    /**
     * 添加用户
     */
    fun putAddUser(userDataAdd: UserDataAdd): Any? {
        val mainObject = JsonObject()
        val subObject = JsonObject()
        val thirdObject = JsonObject()

        subObject.addProperty("employeeNo", userDataAdd.employeeNo)
        subObject.addProperty("name", userDataAdd.name)
        subObject.addProperty("userType", "normal")
        subObject.addProperty("roomNumber", 1)
        subObject.addProperty("floorNumber", 1)

        thirdObject.addProperty("enable", true)
        thirdObject.addProperty("beginTime", "2021-03-01T00:00:00")
        thirdObject.addProperty("endTime", "2031-02-28T23:59:59")
        subObject.add("Valid", thirdObject)

        subObject.addProperty("checkUser", false)
        subObject.addProperty("dynamicCode", "")
        subObject.addProperty("localUIRight", false)
        subObject.addProperty("maxOpenDoorTime", 9)
        mainObject.add("UserInfo", subObject)

        return Apis.addUser.sync(mainObject)
    }

    /**
     * 删除用户
     */
    fun putDelUser(userId: String): Any? {
        val mainObject = JsonObject()
        val subObject = JsonObject()
        val thirdObject = JsonObject()
        val arrayObject = JsonArray()

        thirdObject.addProperty("employeeNo", userId)
        arrayObject.add(thirdObject)
        subObject.add("EmployeeNoList", arrayObject)

        mainObject.add("UserInfoDelCond", subObject)

        return Apis.delUser.sync(mainObject)
    }

    /**
     * 获取指纹数据
     */
    fun getFpData(): Any? {
        val mainObject = JsonObject()
        val subObject = JsonObject()

        subObject.addProperty("searchID", "1000")
        subObject.addProperty("employeeNo", "FP000001")
        subObject.addProperty("cardReaderNo", 1)
        subObject.addProperty("fingerPrintID", 1)
        mainObject.add("FingerPrintCond", subObject)

        return Apis.getFpData.sync(mainObject)
    }

    /**
     * 获取卡片数据
     */
    fun getCardData(): Any? {
        val mainObject = JsonObject()
        val subObject = JsonObject()
        val thirdObject = JsonObject()
        val arrayObject = JsonArray()

        subObject.addProperty("searchID", "1000")
        subObject.addProperty("searchResultPosition", 0)
        subObject.addProperty("maxResults", 30)

//        thirdObject.addProperty("employeeNo", "1")
        thirdObject.addProperty("employeeNo", "CARD000001")
        arrayObject.add(thirdObject)
        subObject.add("EmployeeNoList", arrayObject)

        mainObject.add("CardInfoSearchCond", subObject)

        return Apis.getCardData.sync(mainObject)
    }

    /**
     * 设置采集预处理
     */
    fun setWindowMode(authModeType: AuthModeType, enrollModeType: EnrollModeType): Any? {
        val mainObject = JsonObject()

        mainObject.addProperty("authMode", authModeType.ordinal)
        mainObject.addProperty("detail", enrollModeType.ordinal)

        return Apis.setWindowMode.sync(mainObject)
    }

    /**
     * 采集指纹
     */
    fun fpCapture(call: ApiCall<CaptureFingerPrint>, captureFingerPrintCond: CaptureFingerPrintCond): Future<*>? {
        return Apis.fpCapture.async(call, captureFingerPrintCond)
    }

    /**
     * 采集人脸
     */
    fun faceCapture(call: ApiCall<CaptureFaceData>, captureFaceDataCond: CaptureFaceDataCond): Future<*>? {
        return Apis.faceCapture.async(call, captureFaceDataCond)
    }

    /**
     * 采集卡片
     */
    fun cardCapture(call: ApiCall<CaptureCardData>): Future<*>? {
        return Apis.cardCapture.async(call)
    }
}
