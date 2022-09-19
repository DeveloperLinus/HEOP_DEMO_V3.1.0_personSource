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
package com.hik.app.model;

import android.content.Context;

import com.hik.app.model.data.LocalDataSource;
import com.hik.app.model.data.LocalDataSourceImpl;
import com.hik.archi.utils.SPUtils.RapidSP;

/**
 * Created by linzijian on 2021/2/7.
 */
public class Repository {
    public static BaseRepository provide() {
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();
        return BaseRepository.getInstance(localDataSource);
    }

    public static void init(Context context) {
        //初始化Rsp存储
        RapidSP.init(context);
    }
}