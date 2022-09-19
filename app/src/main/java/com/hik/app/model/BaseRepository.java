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

import androidx.annotation.NonNull;

import com.hik.app.model.data.LocalDataSource;
import com.hik.proto.data.param.VerifyModeType;

import java.util.HashMap;

/**
 * Created by linzijian on 2021/2/7.
 */
public class BaseRepository implements LocalDataSource {
    private volatile static BaseRepository INSTANCE = null;
    private final LocalDataSource mLocalDataSource;

    private BaseRepository(@NonNull LocalDataSource localDataSource) {
        this.mLocalDataSource = localDataSource;
    }

    static BaseRepository getInstance(LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (BaseRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BaseRepository(localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void setActivateStatus(Boolean isEnable) {
        mLocalDataSource.setActivateStatus(isEnable);
    }

    @Override
    public Boolean getActivateStatus() {
        return mLocalDataSource.getActivateStatus();
    }

    @Override
    public void saveVerifyMode(HashMap<VerifyModeType, Boolean> hashMap) {
        mLocalDataSource.saveVerifyMode(hashMap);
    }

    @Override
    public HashMap<VerifyModeType, Boolean> getVerifyMode() {
        return mLocalDataSource.getVerifyMode();
    }
}
