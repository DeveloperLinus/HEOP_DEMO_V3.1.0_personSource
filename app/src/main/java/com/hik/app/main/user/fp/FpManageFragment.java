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
package com.hik.app.main.user.fp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hik.app.BR;
import com.hik.app.R;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseFragment;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/15 17:54
 **/
public class FpManageFragment extends BaseFragment {

    private FpManageViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(FpManageViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_fp_manage, BR.vm, mState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        //获取人员数据
        mState.getFpData();

        // 把拿到数据并放在适配器上
        FpAdapter adapter = new FpAdapter(getContext(), R.layout.adapter_fp_item, mState.fpList);

        // 将适配器上的数据传递给listView
        assert view != null;
        ListView listView = (ListView) view.findViewById(R.id.fp_manage_list_view);
        if (null == listView) {
            Toast.makeText(requireContext(), "listView 空指针", Toast.LENGTH_SHORT).show();
        }
        if (listView != null) {
            listView.setAdapter(adapter);
        }

        mState.go_back.observe(getViewLifecycleOwner(), b -> {
            if (b) {
                Toast.makeText(requireContext(), "设置成功", Toast.LENGTH_SHORT).show();
            }
            nav().navigateUp();
        });

        mState.pAddfp.observe(getViewLifecycleOwner(), b -> {
            if (b) {
                nav().navigate(R.id.action_fpManageFragment_to_fpCaptureFragment);
            }
        });

        return view;
    }
}