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
package com.hik.app.main.user.card;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.archi.utils.KLog;
import com.hik.proto.ApiHelper;
import com.hik.proto.base.ApiCall;
import com.hik.proto.data.bean.ResponseStatus;
import com.hik.proto.data.bean.user.authmode.AuthModeType;
import com.hik.proto.data.bean.user.authmode.EnrollModeType;
import com.hik.proto.data.bean.user.card.CaptureCardData;
import com.hik.proto.data.param.StatusCodeType;

import java.util.Objects;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/15 18:02
 **/
public class CardCaptureViewModel extends ViewModel {
    private static final String TAG = "CardCaptureViewModel";

    MutableLiveData<Boolean> go_back = new MutableLiveData<>();
    MutableLiveData<Boolean> set_capture_mode_flg = new MutableLiveData<>();

    /**
     * 设置采集预处理
     */
    public void setEnrollMode(AuthModeType authMode, EnrollModeType detail) {
        Object o = ApiHelper.INSTANCE.setWindowMode(authMode, detail);
        if (o instanceof ResponseStatus) {
            //noinspection ConstantConditions
            if (((ResponseStatus) o).getStatusCode() == StatusCodeType.OK.getValue()) {
                if (AuthModeType.disable_auth == authMode && EnrollModeType.unknow == detail) {
                    KLog.e(TAG, "reset_auth_mode ok");
                } else {
                    KLog.e(TAG, "setEnrollMode ok");
                    set_capture_mode_flg.setValue(true);
                }
            } else {
                KLog.e(TAG, "setEnrollMode onError: " + ((ResponseStatus) o).getSubStatusCode());
            }
        } else {
            KLog.e(TAG, "setEnrollMode onError");
        }
    }

    /**
     * 卡片采集
     */
    void startCardCapture() {
        ApiHelper.INSTANCE.cardCapture(new ApiCall<CaptureCardData>() {
            @Override
            public void onSuccess(CaptureCardData captureCardData) {
                KLog.e(TAG, "cardCapture OK. cardNo: " + Objects.requireNonNull(captureCardData.getCardInfo()).getCardNo());
                go_back.postValue(true);
            }

            @Override
            public void onError() {
                KLog.e(TAG, "cardCapture onError");
                go_back.postValue(false);
            }
        });
    }
}
