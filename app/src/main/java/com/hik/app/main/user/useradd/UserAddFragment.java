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
package com.hik.app.main.user.useradd;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.hik.app.BR;
import com.hik.app.R;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseFragment;
import com.hik.archi.utils.KLog;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/26 16:20
 **/
public class UserAddFragment extends BaseFragment {

    private UserAddViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(UserAddViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_user_add, BR.vm, mState);
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

        mState.id_null.observe(this, b -> {
            if (b) {
                Toast.makeText(requireContext(), "工号不能为空", Toast.LENGTH_SHORT).show();
            }
        });

        mState.user_data_dialog.observe(this, ObserverUserDataDialog());
    }

    @NonNull
    private Observer<Integer> ObserverUserDataDialog() {
        return i -> {

            if (2 == i) {
                nav().navigate(R.id.action_userAddFragment_to_cardManageFragment);
                return;
            } else if (3 == i) {
                nav().navigate(R.id.action_userAddFragment_to_faceCaptureFragment);

                //页面跳转,并传参
//                Bundle bundle = new Bundle();
//                bundle.putBoolean("faceCapture", true);
//                nav().navigate(R.id.action_userAddFragment_to_accessFragment, bundle);
                return;
            } else if (4 == i) {
                nav().navigate(R.id.action_userAddFragment_to_fpManageFragment);
                return;
            }

            String title = "";
            String content = "";
            switch (i) {
                case 0:
                    title = "工号";
                    content = mState.userId.get();
                    break;
                case 1:
                    title = "姓名";
                    content = mState.userName.get();
                    break;
                case 5:
                    title = "用户权限";
                    content = mState.userRight.get();
                    break;
                default:
                    break;
            }

            if (content == null) {
                content = "";
            }

            switch (i) {
                case 0:
                    {
                        new UserDataDialog(requireContext()).show_userId(title, content, new UserDataCallback() {
                            @Override
                            public void onPositive(String s) {
                                if (s.equals("")) {
                                    Toast.makeText(requireContext(), "工号不能为空", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if (s.length() > 32) {
                                    Toast.makeText(requireContext(), "工号长度大于32", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                mState.userId.set(s);
                            }

                            @Override
                            public void onNegative() {
                                KLog.d(i + " | onNegative");
                            }
                        });
                    }
                    break;
                case 1:
                    {
                        new UserDataDialog(requireContext()).show_userName(title, content, new UserDataCallback() {
                            @Override
                            public void onPositive(String s) {
                                if (s.equals("")) {
                                    Toast.makeText(requireContext(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if (s.length() > 20) {
                                    Toast.makeText(requireContext(), "姓名长度大于20", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                mState.userName.set(s);
                            }

                            @Override
                            public void onNegative() {
                                KLog.d(i + " | onNegative");
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        };
    }
}