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
package com.hik.app.main.config;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.archi.ui.adapter.binding.BindingCommand;

/**
 * Created by linzijian on 2021/3/3.
 */
public class ConfigViewModel extends ViewModel {

    MutableLiveData<Object> config_usermanage_list = new MutableLiveData<>();
    MutableLiveData<Object> config_ethernet_list = new MutableLiveData<>();
    MutableLiveData<Object> config_verifymode_list = new MutableLiveData<>();
    MutableLiveData<Object> go_back = new MutableLiveData<>();

    public BindingCommand config_usermanage = new BindingCommand(() -> config_usermanage_list.setValue(null));
    public BindingCommand config_ethernet = new BindingCommand(() -> config_ethernet_list.setValue(null));
    public BindingCommand config_verifymode = new BindingCommand(() -> config_verifymode_list.setValue(null));
    public BindingCommand cancel_config = new BindingCommand(() -> go_back.setValue(null));
}
