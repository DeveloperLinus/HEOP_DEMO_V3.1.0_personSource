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

import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.databinding.BindingAdapter;

import com.hik.archi.ui.adapter.binding.BindingCommand;

/**
 * Created by linzijian on 2021/3/5.
 */
public class ViewAdapter {

    @BindingAdapter(value = {"onFocusChange"}, requireAll = false)
    public static void setOnFocusChangeListener(View view, BindingCommand<Boolean> onFocusChange) {
        view.setOnFocusChangeListener((v, hasFocus) -> onFocusChange.execute(hasFocus));
    }

    @BindingAdapter(value = {"onClick"}, requireAll = false)
    public static void onClickListener(View view, BindingCommand onClick) {
        view.setOnClickListener(v -> onClick.execute());
    }

    @BindingAdapter(value = {"onLongClick"}, requireAll = false)
    public static void setOnLongClickListener(View view, BindingCommand onLongClick) {
        view.setOnLongClickListener(v -> {
            onLongClick.execute();
            return false;
        });
    }

    @BindingAdapter(value = {"mAdapter"}, requireAll = false)
    public static void setAdapter(ListView view, ListAdapter adapter) {
        view.setAdapter(adapter);
    }
}
