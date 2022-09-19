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
package com.hik.app.model.data;

import com.hik.proto.data.param.VerifyModeType;

import java.util.HashMap;

/**
 * Created by linzijian on 2021/3/5.
 */
public interface LocalDataSource {
    /**
     * 保存激活状态
     */
    void setActivateStatus(Boolean isEnable);

    /**
     * 获取激活状态
     */
    Boolean getActivateStatus();

    /**
     * 保存认证模式
     */
    void saveVerifyMode(HashMap<VerifyModeType, Boolean> hashMap);

    /**
     * 获取认证模式
     */
    HashMap<VerifyModeType, Boolean> getVerifyMode();
}
