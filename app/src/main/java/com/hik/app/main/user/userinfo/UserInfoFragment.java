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
package com.hik.app.main.user.userinfo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.hik.app.BR;
import com.hik.app.R;
import com.hik.app.main.user.useradd.UserDataCallback;
import com.hik.app.main.user.useradd.UserDataDialog;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseFragment;
import com.hik.archi.utils.KLog;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/26 16:20
 **/
public class UserInfoFragment extends BaseFragment {

    private UserInfoViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(UserInfoViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_user_info, BR.vm, mState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //接收参数
        Bundle bundle = getArguments();
        String str = null;
        if (bundle != null) {
            str = bundle.getString("userId", "null");
        }
        KLog.e("userId:" + str);
        mState.getUserInfo(str);
        mState.tip_get_again.observe(this, b -> {
            if (b) {
                Toast.makeText(requireContext(), "请重新获取", Toast.LENGTH_SHORT).show();
            }
        });

        mState.go_back.observe(this, b -> {
            if (b) {
                Toast.makeText(requireContext(), "设置成功", Toast.LENGTH_SHORT).show();
            }
            nav().navigateUp();
        });

        mState.tip_id_null.observe(this, b -> {
            if (b) {
                Toast.makeText(requireContext(), "工号不能为空", Toast.LENGTH_SHORT).show();
            }
        });

        mState.tip_del_err.observe(this, b -> {
            if (b) {
                Toast.makeText(requireContext(), "删除失败", Toast.LENGTH_SHORT).show();
            }
        });

        mState.user_data_dialog.observe(this, ObserverUserDataDialog());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @NonNull
    private Observer<Integer> ObserverUserDataDialog() {
        return i -> {
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
                case 2:
                    title = "卡片";
                    content = mState.userCard.get();
                    break;
                case 3:
                    title = "人脸";
                    content = mState.userFace.get();
                    break;
                case 4:
                    title = "指纹";
                    content = mState.userFp.get();
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
                case 2:
                    {
                        new UserDataDialog(requireContext()).show_userCard(title, content, new UserDataCallback() {
                            @Override
                            public void onPositive(String s) {
                                if (s.equals("")) {
                                    Toast.makeText(requireContext(), "卡号不能为空", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if (s.length() > 20) {
                                    Toast.makeText(requireContext(), "卡号长度大于20", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                mState.userCard.set(s);
                            }

                            @Override
                            public void onNegative() {
                                KLog.d(i + " | onNegative");
                            }
                        });
                    }
                    break;
                case 3:
                    {
                        new UserDataDialog(requireContext()).show_userFace(title, content, new UserDataCallback() {
                            @Override
                            public void onPositive(String s) {
                                if (s.equals("")) {
                                    Toast.makeText(requireContext(), "人脸不能为空", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if (s.length() > 20) {
                                    Toast.makeText(requireContext(), "人脸名长度大于20", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                mState.userFace.set(s);
                            }

                            @Override
                            public void onNegative() {
                                KLog.d(i + " | onNegative");
                            }
                        });
                    }
                    break;
                case 4:
                    {
                        new UserDataDialog(requireContext()).show_userFp(title, content, new UserDataCallback() {
                            @Override
                            public void onPositive(String s) {
                                if (s.equals("")) {
                                    Toast.makeText(requireContext(), "指纹不能为空", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if (s.length() > 20) {
                                    Toast.makeText(requireContext(), "指纹长度大于20", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                mState.userFp.set(s);
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