/*
 * Copyright 2020-present hikvision
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain user_icon_bg copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hik.app.main.user.card;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hik.app.BR;
import com.hik.app.R;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseFragment;
import com.hik.proto.data.bean.user.authmode.AuthModeType;
import com.hik.proto.data.bean.user.authmode.EnrollModeType;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/15 17:54
 **/
public class CardCaptureFragment extends BaseFragment {

    private CardCaptureViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(CardCaptureViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_card_capture, BR.vm, mState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        KLog.e("=== cardCapture ok. cardNo: " + CardUtils.CaptureCard());
//        nav().navigateUp();

        //设置采集预处理
        mState.setEnrollMode(AuthModeType.enroll, EnrollModeType.card);

        mState.set_capture_mode_flg.observe(this, b -> {
            if (b) {
                Toast.makeText(requireContext(), "开始采集", Toast.LENGTH_SHORT).show();
                // 开始采集
                mState.startCardCapture();
            }
        });

        mState.go_back.observe(this, b -> {
            if (b) {
                Toast.makeText(requireContext(), "采集成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(requireContext(), "采集失败", Toast.LENGTH_SHORT).show();
            }

            //重置认证状态
            mState.setEnrollMode(AuthModeType.disable_auth, EnrollModeType.unknow);

            nav().navigateUp();
        });
    }
}