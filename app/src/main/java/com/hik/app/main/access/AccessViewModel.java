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
package com.hik.app.main.access;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.archi.ui.adapter.binding.BindingCommand;

/**
 * Created by linzijian on 2021/3/3.
 */
public class AccessViewModel extends ViewModel {

    MutableLiveData<Object> goConfig = new MutableLiveData<>();

    public MutableLiveData<Integer> authenticationVisible = new MutableLiveData<>(View.GONE);

    public ObservableField<Drawable> authenticationBackground = new ObservableField<>();
    public ObservableField<Drawable> authenticationImgResult = new ObservableField<>();
    public ObservableField<String> authenticationResultText = new ObservableField<>();
    public ObservableField<String> authenticationResultTipsText = new ObservableField<>();

    public BindingCommand onLongClick = new BindingCommand(() -> goConfig.setValue(null));
}
