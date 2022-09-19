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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hik.app.BR;
import com.hik.app.R;
import com.hik.app.main.user.adapter.UserAdapter;
import com.hik.app.main.user.adapter.UserInfoShow;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseFragment;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/15 17:54
 **/
public class UserManageFragment extends BaseFragment {

    private UserManageViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(UserManageViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_user_manage, BR.vm, mState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        //获取人员数据
        mState.getUserData();

        // 把拿到数据并放在适配器上
        UserAdapter adapter = new UserAdapter(getContext(), R.layout.adapter_user_item, mState.userList);

        // 将适配器上的数据传递给listView
        assert view != null;
        ListView listView = (ListView) view.findViewById(R.id.user_manage_list_view); //替换【findViewById --> BindingAdapter】待实现
        if (null == listView) {
            Toast.makeText(requireContext(), "listView 空指针", Toast.LENGTH_SHORT).show();
        }
        if (listView != null) {
            listView.setAdapter(adapter);
        }

        // 为ListView注册一个监听器，当用户点击了ListView中的任何一个子项时，就会回调onItemClick()方法
        // 在这个方法中可以通过position参数判断出用户点击的是那一个子项
        assert listView != null;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInfoShow user = mState.userList.get(position);
                Toast.makeText(requireContext(), user.getEmployeeNo()+"被点击了", Toast.LENGTH_SHORT).show();

                //页面跳转,并传参
                Bundle bundle = new Bundle();
                bundle.putString("userId", user.getEmployeeNo());
                nav().navigate(R.id.action_userManageFragment_to_userInfoFragment, bundle);
            }
        });

        mState.go_back.observe(getViewLifecycleOwner(), b -> {
            if (b) {
                Toast.makeText(requireContext(), "设置成功", Toast.LENGTH_SHORT).show();
            }
            nav().navigateUp();
        });

        mState.pAddUser.observe(getViewLifecycleOwner(), b -> {
            if (b) {
                nav().navigate(R.id.action_userManageFragment_to_userAddFragment);
            }
        });

        return view;
    }
}