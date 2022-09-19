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

import android.content.Context;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hik.app.R;

import java.util.ArrayList;

/**
 * Created by linzijian on 2021/3/9.
 */
class EthernetDialog {

    private Context context;
    private ArrayList<EditText> mList;

    EthernetDialog(Context context) {
        this.context = context;
    }

    void show(@NonNull String title, @NonNull String content, @NonNull EthernetCallback callback) {
        MaterialDialog ethernet = new MaterialDialog.Builder(context)
                .title(title)
                .customView(R.layout.dialog_ethernet, false)
                .positiveText("确认")
                .negativeText("取消")
                .onAny((dialog, which) -> {
                    if (which == DialogAction.POSITIVE) {
                        StringBuilder s = new StringBuilder();
                        for (EditText i : mList) {
                            s.append(i.getText()).append(".");
                        }
                        s.deleteCharAt(s.length() - 1);
                        callback.onPositive(s.toString());
                    } else {
                        callback.onNegative();
                    }
                })
                .show();
        mList = new ArrayList<>();
        mList.add((EditText) ethernet.findViewById(R.id.et_0));
        mList.add((EditText) ethernet.findViewById(R.id.et_1));
        mList.add((EditText) ethernet.findViewById(R.id.et_2));
        mList.add((EditText) ethernet.findViewById(R.id.et_3));
        String[] split = content.split("\\.");
        if (split.length != 4) {
            content = "0.0.0.0";
            split = content.split("\\.");
        }
        for (EditText i : mList) {
            i.addTextChangedListener(new EthernetTextWatcher(mList, i));
            i.setText(split[mList.indexOf(i)]);
        }
    }
}
