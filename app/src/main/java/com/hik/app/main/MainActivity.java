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
package com.hik.app.main;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hik.app.BR;
import com.hik.app.R;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseActivity;
import com.hik.proto.event.ApiListener;

/**
 * Created by linzijian on 2021/3/2.
 */
public class MainActivity extends BaseActivity {

    private MainViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(MainViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main, BR.vm, mState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化ApiListener事件监听
        getLifecycle().addObserver(new ApiListener());
    }
}
