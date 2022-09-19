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
package com.hik.app.activate;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.app.R;
import com.hik.app.model.BaseRepository;
import com.hik.app.model.Repository;
import com.hik.archi.ui.adapter.binding.BindingCommand;
import com.hik.archi.utils.KLog;
import com.hik.proto.ApiHelper;
import com.hik.proto.base.ApiCall;
import com.hik.proto.data.bean.ActivateStatus;
import com.hik.proto.data.bean.ResponseStatus;
import com.hik.proto.data.param.StatusCodeType;
import com.hik.proto.data.param.VerifyModeType;

import java.util.HashMap;

/**
 * Created by linzijian on 2021/3/5.
 */
public class ActivateViewModel extends ViewModel {

    private static final String TAG = "ActivateViewModel";

    private static BaseRepository mModel = Repository.provide();

    private MutableLiveData<Boolean> passShowSwitch = new MutableLiveData<>();

    MutableLiveData<Boolean> pActivateStatus = new MutableLiveData<>();

    public ObservableInt clearBtnVisibility = new ObservableInt(View.GONE);
    public ObservableField<String> userName = new ObservableField<>("admin");
    public ObservableField<String> password = new ObservableField<>("hik12345");
    public ObservableField<Integer> imageResource = new ObservableField<>(R.mipmap.show_psw_press);
    public ObservableField<TransformationMethod> transformationMethod = new ObservableField<>(PasswordTransformationMethod.getInstance());

    public BindingCommand<Boolean> setOnFocusChangeListener = new BindingCommand<>(hasFocus -> {
        if (hasFocus) {
            clearBtnVisibility.set(View.VISIBLE);
        } else {
            clearBtnVisibility.set(View.INVISIBLE);
        }
    });
    public BindingCommand clearUserName = new BindingCommand(() -> userName.set(""));
    public BindingCommand passwordShowSwitch = new BindingCommand(() -> {
        if ((passShowSwitch.getValue() == null) || !passShowSwitch.getValue()){
            imageResource.set(R.mipmap.show_psw);
            transformationMethod.set(HideReturnsTransformationMethod.getInstance());
            passShowSwitch.setValue(true);
        } else {
            imageResource.set(R.mipmap.show_psw_press);
            transformationMethod.set(PasswordTransformationMethod.getInstance());
            passShowSwitch.setValue(false);
        }
    });
    public BindingCommand login = new BindingCommand(this::setActivate);

    /**
     * 激活设备
     */
    private void setActivate() {
        if (TextUtils.isEmpty(userName.get())) {
            KLog.e(TAG, "请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            KLog.e(TAG, "请输入密码！");
            return;
        }
        if (!mModel.getActivateStatus()) {
            Object o = ApiHelper.INSTANCE.activateDevice(password.get());
            if (o instanceof ResponseStatus) {
                if (((ResponseStatus) o).getStatusCode() != StatusCodeType.OK.getValue()) {
                    KLog.e(TAG, "activateDevice onError: " + ((ResponseStatus) o).getSubStatusCode());
                } else {
                    pActivateStatus.setValue(true);
                    mModel.setActivateStatus(true);
                }
            } else {
                KLog.e(TAG, "activateDevice onError");
            }
        } else {
            pActivateStatus.setValue(true);
        }
    }

    /**
     * 获取激活状态
     */
    void getActivate() {
        ApiHelper.INSTANCE.getActivateStatus(new ApiCall<ActivateStatus>() {
            @Override
            public void onSuccess(ActivateStatus activateStatus) {
                pActivateStatus.postValue(activateStatus.getActivated());
                mModel.setActivateStatus(activateStatus.getActivated());
            }

            @Override
            public void onError() {
                KLog.e(TAG, "getActivateStatus onError");
            }
        });
    }

    /**
     * 设置初始认证模式
     */
    void defaultVerifyMode() {
        HashMap<VerifyModeType, Boolean> verifyMode = new HashMap<>();
        verifyMode.put(VerifyModeType.face, true);
        verifyMode.put(VerifyModeType.card, true);
        verifyMode.put(VerifyModeType.fingerPrint, true);
        verifyMode.put(VerifyModeType.QRCode, false);
        ApiHelper.INSTANCE.setVerifyMode(new ApiCall<ResponseStatus>() {
            @Override
            public void onSuccess(ResponseStatus responseStatus) {
                if (responseStatus.getStatusCode() == StatusCodeType.OK.getValue()) {
                    mModel.saveVerifyMode(verifyMode);
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
