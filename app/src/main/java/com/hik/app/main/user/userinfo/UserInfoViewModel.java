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

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.archi.ui.adapter.binding.BindingCommand;
import com.hik.archi.utils.KLog;
import com.hik.proto.ApiHelper;
import com.hik.proto.data.bean.ResponseStatus;
import com.hik.proto.data.bean.user.UserDataAdd;
import com.hik.proto.data.bean.user.user_search.UserDataSearch;
import com.hik.proto.data.bean.user.user_search.UserInfoSearchGet;
import com.hik.proto.data.param.StatusCodeType;

import java.util.Objects;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/15 18:02
 **/
public class UserInfoViewModel extends ViewModel {
    private static final String TAG = "UserInfoViewModel";

    MutableLiveData<Integer> user_data_dialog = new MutableLiveData<>();
    MutableLiveData<Boolean> go_back = new MutableLiveData<>();
    MutableLiveData<Boolean> tip_id_null = new MutableLiveData<>();
    MutableLiveData<Boolean> tip_get_again = new MutableLiveData<>();
    MutableLiveData<Boolean> tip_del_err = new MutableLiveData<>();

    public ObservableField<String> userId = new ObservableField<>();
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> userCard = new ObservableField<>("未录入");
    public ObservableField<String> userFace = new ObservableField<>("未录入");
    public ObservableField<String> userFp = new ObservableField<>("未录入");
    public ObservableField<String> userRight = new ObservableField<>("普通用户");

    public BindingCommand cancel_config = new BindingCommand(() -> go_back.setValue(false));
    public BindingCommand accept_config = new BindingCommand(this::addUser);
    public BindingCommand config_userId = new BindingCommand(() -> user_data_dialog.setValue(0));
    public BindingCommand config_userName = new BindingCommand(() -> user_data_dialog.setValue(1));
    public BindingCommand config_userCard = new BindingCommand(() -> user_data_dialog.setValue(2));
    public BindingCommand config_userFace = new BindingCommand(() -> user_data_dialog.setValue(3));
    public BindingCommand config_userFp = new BindingCommand(() -> user_data_dialog.setValue(4));
    public BindingCommand config_userRight = new BindingCommand(() -> user_data_dialog.setValue(5));
    public BindingCommand config_userDel = new BindingCommand(this::delUser);

    /**
     * 获取当前人员信息
     */
    void getUserInfo(String user_id) {
        KLog.v(TAG, "getUserInfo user_id:" + user_id);

        Object o = ApiHelper.INSTANCE.getUserInfo(user_id);
        if (o instanceof UserDataSearch) {
            KLog.v(TAG, "=== getUserInfo ok" + Objects.requireNonNull(((UserDataSearch) o).getUserInfoSearch()).getUserInfo());

            for (UserInfoSearchGet userInfoSearch : Objects.requireNonNull(Objects.requireNonNull(((UserDataSearch) o).getUserInfoSearch()).getUserInfo())) {
                userId.set(userInfoSearch.getEmployeeNo());
                userName.set(userInfoSearch.getName());
                //noinspection ConstantConditions
                switch (userInfoSearch.getNumOfCard())
                {
                    case 1:
                        userCard.set("1张");
                        break;
                    case 2:
                        userCard.set("2张");
                        break;
                    case 3:
                        userCard.set("3张");
                        break;
                    case 4:
                        userCard.set("4张");
                        break;
                    case 5:
                        userCard.set("5张");
                        break;
                    default:
                        userCard.set("0张");
                        break;
                }
                //noinspection ConstantConditions
                if (userInfoSearch.getNumOfFace() > 0)  {
                    userFace.set("已录入");
                }
                //noinspection ConstantConditions
                switch (userInfoSearch.getNumOfFP())
                {
                    case 1:
                        userFp.set("1枚");
                        break;
                    case 2:
                        userFp.set("2枚");
                        break;
                    case 3:
                        userFp.set("3枚");
                        break;
                    case 4:
                        userFp.set("4枚");
                        break;
                    case 5:
                        userFp.set("5枚");
                        break;
                    default:
                        userFp.set("0枚");
                        break;
                }
                //noinspection ConstantConditions
                if (userInfoSearch.getLocalUIRight())  {
                    userRight.set("管理员");
                }
            }
        } else {
            KLog.e(TAG, "=== getUserInfo onError");
            tip_get_again.setValue(true);
        }
    }

    /**
     * 删除当前人员信息
     */
    void delUser() {
        KLog.e(TAG, "delUser test");

        if (null == userId.get())
        {
            tip_id_null.setValue(true);
            KLog.e(TAG, "length 0");
            return;
        }

        Object o = ApiHelper.INSTANCE.putDelUser(Objects.requireNonNull(userId.get()));
        if (o instanceof ResponseStatus) {
            //noinspection ConstantConditions
            if (((ResponseStatus) o).getStatusCode() == StatusCodeType.OK.getValue()) {
                go_back.setValue(true);
            } else {
                KLog.e(TAG, "addUser onError: " + ((ResponseStatus) o).getSubStatusCode());
            }
        }  else {
            KLog.e(TAG, "=== getUserInfo onError");
            tip_del_err.setValue(true);
        }
    }

    /**
     * 增加当前人员信息
     */
    private void addUser() {
        KLog.e(TAG, "addUser test");

        if (null == userId.get())
        {
            tip_id_null.setValue(true);
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
