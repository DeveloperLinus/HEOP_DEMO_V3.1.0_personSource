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

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hik.app.BR;
import com.hik.app.R;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseFragment;

/**
 * Created by linzijian on 2021/3/3.
 */
public class ConfigFragment extends BaseFragment {

    private ConfigViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(ConfigViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_config, BR.vm, mState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mState.config_usermanage_list.observe(this, o -> nav().navigate(R.id.action_configFragment_to_userManageFragment));
        mState.config_ethernet_list.observe(this, o -> nav().navigate(R.id.action_configFragment_to_ethernetFragment));
        mState.config_verifymode_list.observe(this, o -> nav().navigate(R.id.action_configFragment_to_verifyModeFragment));
        mState.go_back.observe(this, b -> nav().navigateUp());
    }
}
