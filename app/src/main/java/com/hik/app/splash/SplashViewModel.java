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
package com.hik.app.splash;

import android.media.MediaPlayer;
import android.net.Uri;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.app.model.BaseRepository;
import com.hik.app.model.Repository;
import com.hik.archi.ui.adapter.binding.BindingCommand;
import com.hik.proto.ApiHelper;
import com.hik.proto.base.ApiCall;
import com.hik.proto.data.bean.ActivateStatus;

/**
 * Created by linzijian on 2021/3/4.
 */
public class SplashViewModel extends ViewModel {

    private static final String TAG = "SplashViewModel";

    private static BaseRepository mModel = Repository.provide();

    MutableLiveData<Object> pOnCompletion = new MutableLiveData<>();
    MutableLiveData<Boolean> pActivateStatus = new MutableLiveData<>();
    MutableLiveData<Object> pSystemDown = new MutableLiveData<>();

    public ObservableField<Uri> videoUri = new ObservableField<>();

    public BindingCommand<MediaPlayer> setOnPreparedListener = new BindingCommand<>(MediaPlayer::start);
    public BindingCommand<MediaPlayer> setOnCompletionListener = new BindingCommand<>(mediaPlayer -> pOnCompletion.setValue(null));

    /**
     * 获取激活状态
     */
    void getActivate() {
        ApiHelper.INSTANCE.getActivateStatus(new ApiCall<ActivateStatus>() {
            @Override
            public void onSuccess(ActivateStatus activateStatus) {
                //子线程需要用postValue更新数据
                pActivateStatus.postValue(activateStatus.getActivated());
                mModel.setActivateStatus(activateStatus.getActivated());
            }

            @Override
            public void onError() {
                pSystemDown.postValue(null);
            }
        });
    }
}
