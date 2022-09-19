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

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hik.app.BR;
import com.hik.app.R;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseFragment;

/**
 * Created by linzijian on 2021/3/10.
 */
public class VerifyModeFragment extends BaseFragment {

    private VerifyModeViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(VerifyModeViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_verifymode, BR.vm, mState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mState.go_back.observe(this, b -> {
            if (b) {
                Toast.makeText(requireContext(), "设置成功", Toast.LENGTH_SHORT).show();
            }
            nav().navigateUp();
        });
    }
}
