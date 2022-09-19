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
 * 按键操作事件
 */
data class KeyEventBean(
    var ipAddress: String? = null,
    var ipv6Address: String? = null,
    var portNo: Int = 0,
    var protocol: String? = null,
    var macAddress: String? = null,
    var channelID: Int = 0,
    var dateTime: String? = null,
    var activePostCount: Int = 0,
    var eventType: String? = null,
    var eventState: String? = null,
    var eventDescription: String? = null,
    var KeyEvent: KeyEvent? = null
)

data class KeyEvent(
    var keyValue: Int = 0,
    var keyType: String? = null
)

enum class KeyType {
    press,         //按下
    lift,          //抬起
    pressAndHold   //按住
}

//物理按键对应值
const val key_0 = 11                 //数字键：0
const val key_1 = 2                  //数字键：1
const val key_2 = 3                  //数字键：2
const val key_3 = 4                  //数字键：3
const val key_4 = 5                  //数字键：4
const val key_5 = 6                  //数字键：5
const val key_6 = 7                  //数字键：6
const val key_7 = 8                  //数字键：7
const val key_8 = 9                  //数字键：8
const val key_9 = 10                 //数字键：9
const val key_sos_center = 63        //报警呼叫键
const val key_back_or_hang_up = 64   //*键
const val key_center_or_unlock = 65  //#键
const val key_CALL = 66              //呼叫键
const val key_center = 67            //呼叫中心键
