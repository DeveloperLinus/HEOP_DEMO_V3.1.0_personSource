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

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hik.app.BR;
import com.hik.app.main.MainActivity;
import com.hik.app.R;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseActivity;

/**
 * Created by linzijian on 2021/3/5.
 */
public class ActivateActivity extends BaseActivity {

    private ActivateViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(ActivateViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_activate, BR.vm, mState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mState.pActivateStatus.observe(this, b -> {
            if (b) {
                mState.defaultVerifyMode();
                startActivity(new Intent(ActivateActivity.this, MainActivity.class));
                finish();
            } else {
                mState.getActivate();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mState.getActivate();
    }
}
