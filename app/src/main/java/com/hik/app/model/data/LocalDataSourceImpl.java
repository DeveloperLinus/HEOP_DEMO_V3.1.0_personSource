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

import com.hik.archi.utils.SPUtils.RapidSP;
import com.hik.proto.data.param.VerifyModeType;

import java.util.HashMap;

/**
 * Created by linzijian on 2021/3/5.
 */
public class LocalDataSourceImpl implements LocalDataSource {

    private static final String TAG = "LocalDataSource";

    private volatile static LocalDataSourceImpl INSTANCE = null;

    public static LocalDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSourceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void setActivateStatus(Boolean isEnable) {
        RapidSP.get(TAG).edit().putBoolean("ActivateStatus", isEnable).apply();
    }

    @Override
    public Boolean getActivateStatus() {
        return RapidSP.get(TAG).getBoolean("ActivateStatus", false);
    }

    @Override
    public void saveVerifyMode(HashMap<VerifyModeType, Boolean> hashMap) {
        Boolean face = hashMap.get(VerifyModeType.face);
        Boolean card = hashMap.get(VerifyModeType.card);
        Boolean fingerPrint = hashMap.get(VerifyModeType.fingerPrint);
        Boolean QRCode = hashMap.get(VerifyModeType.QRCode);
        if (face != null) {
            RapidSP.get(TAG).edit().putBoolean("VerifyMode_Face", face).apply();
        }
        if (card != null) {
            RapidSP.get(TAG).edit().putBoolean("VerifyMode_Card", card).apply();
        }
        if (fingerPrint != null) {
            RapidSP.get(TAG).edit().putBoolean("VerifyMode_FingerPrint", fingerPrint).apply();
        }
        if (QRCode != null) {
            RapidSP.get(TAG).edit().putBoolean("VerifyMode_QRCode", QRCode).apply();
        }
    }

    @Override
    public HashMap<VerifyModeType, Boolean> getVerifyMode() {
        HashMap<VerifyModeType, Boolean> hashMap = new HashMap<>();
        hashMap.put(VerifyModeType.face, RapidSP.get(TAG).getBoolean("VerifyMode_Face", false));
        hashMap.put(VerifyModeType.card, RapidSP.get(TAG).getBoolean("VerifyMode_Card", false));
        hashMap.put(VerifyModeType.fingerPrint, RapidSP.get(TAG).getBoolean("VerifyMode_FingerPrint", false));
        hashMap.put(VerifyModeType.QRCode, RapidSP.get(TAG).getBoolean("VerifyMode_QRCode", false));
        return hashMap;
    }
}
