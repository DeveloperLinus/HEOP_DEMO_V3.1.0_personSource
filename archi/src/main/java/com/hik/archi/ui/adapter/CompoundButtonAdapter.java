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
package com.hik.archi.ui.adapter;

import android.widget.CompoundButton;

import androidx.databinding.BindingAdapter;

import com.hik.archi.ui.adapter.binding.BindingCommand;

/**
 * Created by linzijian on 2021/3/10.
 */
public class CompoundButtonAdapter {

    @BindingAdapter(value = {"onCheckedChange"}, requireAll = false)
    public static void setOnCheckedChangeListener(CompoundButton view, BindingCommand<Boolean> onCheckedChange) {
        view.setOnCheckedChangeListener((buttonView, isChecked) -> onCheckedChange.execute(isChecked));
    }
}
