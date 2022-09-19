/*
 * Copyright 2020-present hikvision
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain user_icon_bg copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hik.proto.data.bean.user.user_search;

import com.thoughtworks.xstream.annotations.XStreamAlias
import java.util.ArrayList

/**
 * Created by liuxu11 on 2021/3/23.
 * description:
 */
data class UserInfoSearch (
    val searchID: String? = null,
    val responseStatusStrg: String? = null,
    val numOfMatches: Int? = null,
    val totalMatches: Int? = null,
    val UserInfo: ArrayList<UserInfoSearchGet>? = null
)