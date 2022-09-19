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

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.archi.ui.adapter.binding.BindingCommand;
import com.hik.archi.utils.KLog;
import com.hik.proto.ApiHelper;
import com.hik.proto.base.ApiCall;
import com.hik.proto.data.bean.ResponseStatus;
import com.hik.proto.data.bean.ethernet.DefaultGateway;
import com.hik.proto.data.bean.ethernet.IPAddress;
import com.hik.proto.data.bean.ethernet.PrimaryDNS;
import com.hik.proto.data.bean.ethernet.SecondaryDNS;
import com.hik.proto.data.param.StatusCodeType;

/**
 * Created by linzijian on 2021/3/10.
 */
public class EthernetViewModel extends ViewModel {

    private static final String TAG = "EthernetViewModel";

    MutableLiveData<Integer> ethernet_dialog = new MutableLiveData<>();
    MutableLiveData<Boolean> go_back = new MutableLiveData<>();

    public ObservableField<String> ipAddress = new ObservableField<>("0.0.0.0");
    public ObservableField<String> subnetMask = new ObservableField<>("0.0.0.0");
    public ObservableField<String> defaultGateway = new ObservableField<>("0.0.0.0");
    public ObservableField<String> primaryDNS = new ObservableField<>("0.0.0.0");
    public ObservableField<String> secondaryDNS = new ObservableField<>("0.0.0.0");

    public BindingCommand cancel_config = new BindingCommand(() -> go_back.setValue(false));
    public BindingCommand accept_config = new BindingCommand(this::setEthernet);
    public BindingCommand config_ipAddress = new BindingCommand(() -> ethernet_dialog.setValue(0));
    public BindingCommand config_subnetMask = new BindingCommand(() -> ethernet_dialog.setValue(1));
    public BindingCommand config_defaultGateway = new BindingCommand(() -> ethernet_dialog.setValue(2));
    public BindingCommand config_primaryDNS = new BindingCommand(() -> ethernet_dialog.setValue(3));
    public BindingCommand config_secondaryDNS = new BindingCommand(() -> ethernet_dialog.setValue(4));

    /**
     * 获取有线网络配置
     */
    void getEthernet() {
        ApiHelper.INSTANCE.getEthernet(new ApiCall<IPAddress>() {
            @Override
            public void onSuccess(IPAddress address) {
                ipAddress.set(address.getIpAddress());
                subnetMask.set(address.getSubnetMask());
                if (address.getDefaultGateway() != null) {
                    defaultGateway.set(address.getDefaultGateway().getIpAddress());
                }
                if (address.getPrimaryDNS() != null) {
                    primaryDNS.set(address.getPrimaryDNS().getIpAddress());
                }
                if (address.getSecondaryDNS() != null) {
                    secondaryDNS.set(address.getSecondaryDNS().getIpAddress());
                }
            }

            @Override
            public void onError() {
                KLog.e(TAG, "getEthernet onError");
            }
        });
    }

    /**
     * 设置有线网络
     */
    private void setEthernet() {
        Object o = ApiHelper.INSTANCE.setEthernet(new IPAddress("v4", "static",
                ipAddress.get(), subnetMask.get(),
                new DefaultGateway(defaultGateway.get()),
                new PrimaryDNS(primaryDNS.get()),
                new SecondaryDNS(secondaryDNS.get())));
        if (o instanceof ResponseStatus) {
            if (((ResponseStatus) o).getStatusCode() == StatusCodeType.OK.getValue()) {
                go_back.setValue(true);
            } else {
                KLog.e(TAG, "setEthernet onError: " + ((ResponseStatus) o).getSubStatusCode());
            }
        } else {
            KLog.e(TAG, "setEthernet onError");
        }
    }
}
