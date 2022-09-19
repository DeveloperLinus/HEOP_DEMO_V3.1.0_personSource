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
package com.hik.app.main.user;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.app.R;
import com.hik.app.main.user.adapter.UserInfoShow;
import com.hik.archi.ui.adapter.binding.BindingCommand;
import com.hik.archi.utils.KLog;
import com.hik.proto.ApiHelper;
import com.hik.proto.data.bean.user.UserData;
import com.hik.proto.data.bean.user.UserInfoRecv;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/15 18:02
 **/
public class UserManageViewModel extends ViewModel {
    private static final String TAG = "UserManageViewModel";

    MutableLiveData<Boolean> go_back = new MutableLiveData<>();
    MutableLiveData<Boolean> pAddUser = new MutableLiveData<>();

    // userList用于存储数据
    List<UserInfoShow> userList = new ArrayList<>();//    List<UserInfo> userList = new LinkedList<>();

    //该适配器待实现
//    public ObservableField<UserAdapter> userAdapter = new ObservableField<>();
    public ObservableField<Integer> pMxCount = new ObservableField<>();

    public BindingCommand cancel_config = new BindingCommand(() -> go_back.setValue(false));
    public BindingCommand add_user = new BindingCommand(() -> pAddUser.setValue(true));

    /**
     * 获取人员信息
     */
    public void getUserData() {
        Object o = ApiHelper.INSTANCE.getUserData();
        if (o instanceof UserData) {
            KLog.v(TAG, Objects.requireNonNull(((UserData) o).getUserInfoGet()).getMaxCount());

            for (UserInfoRecv userInfo : Objects.requireNonNull(Objects.requireNonNull(((UserData) o).getUserInfoGet()).getUserInfo())) {
                //noinspection ConstantConditions
                userList.add(new UserInfoShow(
                        userInfo.getEmployeeNo(),
                        userInfo.getName(),
                        (userInfo.getNumOfFace() > 0) ? R.mipmap.list_icon_face_registered : R.mipmap.list_icon_face_nor,
                        (userInfo.getNumOfFP() > 0) ? R.mipmap.list_icon_fp_registered : R.mipmap.list_icon_fp_nor,
                        (userInfo.getNumOfCard() > 0) ? R.mipmap.list_icon_card_registered : R.mipmap.list_icon_card_nor,
                        R.mipmap.arrow_right));
            }
        } else {
            KLog.e(TAG, "getUserInfo onError");
        }
    }
}
