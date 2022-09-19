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

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.archi.ui.adapter.binding.BindingCommand;
import com.hik.archi.utils.KLog;
import com.hik.proto.ApiHelper;
import com.hik.proto.data.bean.ResponseStatus;
import com.hik.proto.data.bean.user.UserDataAdd;
import com.hik.proto.data.param.StatusCodeType;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/15 18:02
 **/
public class UserAddViewModel extends ViewModel {
    private static final String TAG = "UserAddViewModel";

    MutableLiveData<Integer> user_data_dialog = new MutableLiveData<>();
    MutableLiveData<Boolean> go_back = new MutableLiveData<>();
    MutableLiveData<Boolean> id_null = new MutableLiveData<>();

    public ObservableField<String> userId = new ObservableField<>();
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> userCard = new ObservableField<>("请录入");
    public ObservableField<String> userFace = new ObservableField<>("请录入");
    public ObservableField<String> userFp = new ObservableField<>("请录入");
    public ObservableField<String> userRight = new ObservableField<>("普通用户");

    public BindingCommand cancel_config = new BindingCommand(() -> go_back.setValue(false));
    public BindingCommand accept_config = new BindingCommand(this::addUser);

    public BindingCommand config_userId = new BindingCommand(() -> user_data_dialog.setValue(0));
    public BindingCommand config_userName = new BindingCommand(() -> user_data_dialog.setValue(1));
    public BindingCommand config_userCard = new BindingCommand(() -> user_data_dialog.setValue(2));
    public BindingCommand config_userFace = new BindingCommand(() -> user_data_dialog.setValue(3));
    public BindingCommand config_userFp = new BindingCommand(() -> user_data_dialog.setValue(4));
    public BindingCommand config_userRight = new BindingCommand(() -> user_data_dialog.setValue(5));

    /**
     * 增加当前人员信息
     */
    private void addUser() {
        KLog.e(TAG, "addUser test");

        if (null == userId.get())
        {
            id_null.setValue(true);
            KLog.e(TAG, "length 0");
            return;
        }

        Object o = ApiHelper.INSTANCE.putAddUser(new UserDataAdd(userId.get(), userName.get()));
        if (o instanceof ResponseStatus) {
            //noinspection ConstantConditions
            if (((ResponseStatus) o).getStatusCode() == StatusCodeType.OK.getValue()) {
                go_back.setValue(true);
            } else {
                KLog.e(TAG, "addUser onError: " + ((ResponseStatus) o).getSubStatusCode());
            }
        } else {
            KLog.e(TAG, "addUser onError");
        }
    }
}
