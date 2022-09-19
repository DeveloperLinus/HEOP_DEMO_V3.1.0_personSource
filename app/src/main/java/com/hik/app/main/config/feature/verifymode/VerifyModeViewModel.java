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
package com.hik.app.main.config.feature.verifymode;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.app.model.BaseRepository;
import com.hik.app.model.Repository;
import com.hik.archi.ui.adapter.binding.BindingCommand;
import com.hik.archi.utils.KLog;
import com.hik.proto.ApiHelper;
import com.hik.proto.base.ApiCall;
import com.hik.proto.data.bean.ResponseStatus;
import com.hik.proto.data.param.StatusCodeType;
import com.hik.proto.data.param.VerifyModeType;

import java.util.HashMap;

/**
 * Created by linzijian on 2021/3/10.
 */
public class VerifyModeViewModel extends ViewModel {

    private static final String TAG = "VerifyModeViewModel";

    private HashMap<VerifyModeType, Boolean> verifyMode = new HashMap<>();

    private static BaseRepository mModel = Repository.provide();

    MutableLiveData<Boolean> go_back = new MutableLiveData<>();

    /**
     * ObservableField有防抖的特点,即如果更新的内容与原内容相同则不通知视图刷新.
     * 所以这里采用MutableLiveData,但不可否认ObservableField可以减少不必要的性能开销.
     */
    public MutableLiveData<Boolean> face_checked = new MutableLiveData<>(mModel.getVerifyMode().get(VerifyModeType.face));
    public MutableLiveData<Boolean> card_checked = new MutableLiveData<>(mModel.getVerifyMode().get(VerifyModeType.card));
    public MutableLiveData<Boolean> fp_checked = new MutableLiveData<>(mModel.getVerifyMode().get(VerifyModeType.fingerPrint));
    public MutableLiveData<Boolean> qr_checked = new MutableLiveData<>(mModel.getVerifyMode().get(VerifyModeType.QRCode));

    public BindingCommand cancel_config = new BindingCommand(() -> go_back.setValue(false));
    public BindingCommand accept_config = new BindingCommand(this::setVerifyMode);
    public BindingCommand<Boolean> face_changed = new BindingCommand<>(b -> {
        if (b) {
            qr_checked.setValue(false);
        }
        verifyMode.put(VerifyModeType.face, b);
    });
    public BindingCommand<Boolean> card_changed = new BindingCommand<>(b -> {
        if (b) {
            qr_checked.setValue(false);
        }
        verifyMode.put(VerifyModeType.card, b);
    });
    public BindingCommand<Boolean> fp_changed = new BindingCommand<>(b -> {
        if (b) {
            qr_checked.setValue(false);
        }
        verifyMode.put(VerifyModeType.fingerPrint, b);
    });
    public BindingCommand<Boolean> qr_changed = new BindingCommand<>(b -> {
        if (b) {
            face_checked.setValue(false);
            card_checked.setValue(false);
            fp_checked.setValue(false);
        }
        verifyMode.put(VerifyModeType.QRCode, b);
    });

    /**
     * 设置认证模式
     */
    private void setVerifyMode() {
        ApiHelper.INSTANCE.setVerifyMode(new ApiCall<ResponseStatus>() {
            @Override
            public void onSuccess(ResponseStatus responseStatus) {
                if (responseStatus.getStatusCode() == StatusCodeType.OK.getValue()) {
                    mModel.saveVerifyMode(verifyMode);
                    //子线程需要用postValue更新数据
                    go_back.postValue(true);
                } else {
                    KLog.e(TAG, "setVerifyMode onError: " + responseStatus.getSubStatusCode());
                }
            }

            @Override
            public void onError() {
                KLog.e(TAG, "setVerifyMode onError");
            }
        }, verifyMode);
    }
}
