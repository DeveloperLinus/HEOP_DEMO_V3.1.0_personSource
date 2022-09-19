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
package com.hik.app.main.config.feature.ethernet;

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
 * Created by linzijian on 2021/3/10.
 */
public class EthernetFragment extends BaseFragment {

    private EthernetViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(EthernetViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_ethernet, BR.vm, mState);
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
        mState.ethernet_dialog.observe(this, ObserverEthernetDialog());
    }

    @Override
    public void onResume() {
        super.onResume();
        mState.getEthernet();
    }

    @NonNull
    private Observer<Integer> ObserverEthernetDialog() {
        return i -> {
            String title = "";
            String content = "";
            switch (i) {
                case 0:
                    title = "IPv4地址";
                    content = mState.ipAddress.get();
                    break;
                case 1:
                    title = "子网掩码";
                    content = mState.subnetMask.get();
                    break;
                case 2:
                    title = "默认网关";
                    content = mState.defaultGateway.get();
                    break;
                case 3:
                    title = "主DNS服务器";
                    content = mState.primaryDNS.get();
                    break;
                case 4:
                    title = "备用DNS服务";
                    content = mState.secondaryDNS.get();
                    break;
                default:
                    break;
            }
            if (content == null) {
                content = "";
            }
            new EthernetDialog(requireContext()).show(title, content, new EthernetCallback() {
                @Override
                public void onPositive(String s) {
                    String[] split = s.split("\\.");
                    for (String i : split) {
                        if (i.equals("") || (split.length != 4)) {
                            Toast.makeText(requireContext(), "字段不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    switch (i) {
                        case 0:
                            mState.ipAddress.set(s);
                            break;
                        case 1:
                            mState.subnetMask.set(s);
                            break;
                        case 2:
                            mState.defaultGateway.set(s);
                            break;
                        case 3:
                            mState.primaryDNS.set(s);
                            break;
                        case 4:
                            mState.secondaryDNS.set(s);
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onNegative() {
                    KLog.d(i + " | onNegative");
                }
            });
        };
    }
}
