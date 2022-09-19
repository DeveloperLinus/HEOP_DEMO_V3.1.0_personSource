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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hik.app.BR;
import com.hik.app.main.MainActivity;
import com.hik.app.R;
import com.hik.app.activate.ActivateActivity;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseActivity;

import java.io.File;

/**
 * Created by linzijian on 2021/3/4.
 */
public class SplashActivity extends BaseActivity {

    private SplashViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(SplashViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_splash, BR.vm, mState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mState.videoUri.set(Uri.parse("android.resource://" + getPackageName() + File.separator + R.raw.splash));
        mState.pOnCompletion.observe(this, o -> {
            mState.getActivate();
        });
        mState.pActivateStatus.observe(this, b -> {
            if (b) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashActivity.this, ActivateActivity.class));
                finish();
            }
        });
        mState.pSystemDown.observe(this, o -> {
            mState.getActivate();
            Toast.makeText(this, "系统服务未就绪 ...", Toast.LENGTH_SHORT).show();
        });
    }
}
