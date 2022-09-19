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
package com.hik.app.main.user.useradd;

import android.content.Context;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hik.app.R;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/29 10:27
 **/
public class UserDataDialog {

    private Context context;
    private EditText mEt;

    public UserDataDialog(Context context) {
        this.context = context;
    }

    public void show_userId(@NonNull String title, @NonNull String content, @NonNull UserDataCallback callback) {
        MaterialDialog userData = new MaterialDialog.Builder(context)
                .title(title)
                .customView(R.layout.dialog_user_id, false)
                .positiveText("确认")
                .negativeText("取消")
                .onAny((dialog, which) -> {
                    if (which == DialogAction.POSITIVE) {
                        StringBuilder s = new StringBuilder();
                        s.append(mEt.getText());

                        callback.onPositive(s.toString());
                    } else {
                        callback.onNegative();
                    }
                })
                .show();

        mEt = (EditText) userData.findViewById(R.id.user_id);
        mEt.addTextChangedListener(new UserDataTextWatcher(mEt));
    }

    public void show_userName(@NonNull String title, @NonNull String content, @NonNull UserDataCallback callback) {
        MaterialDialog userData = new MaterialDialog.Builder(context)
                .title(title)
                .customView(R.layout.dialog_user_name, false)
                .positiveText("确认")
                .negativeText("取消")
                .onAny((dialog, which) -> {
                    if (which == DialogAction.POSITIVE) {
                        StringBuilder s = new StringBuilder();
                        s.append(mEt.getText());

                        callback.onPositive(s.toString());
                    } else {
                        callback.onNegative();
                    }
                })
                .show();

        mEt = (EditText) userData.findViewById(R.id.user_name);
        mEt.addTextChangedListener(new UserDataTextWatcher(mEt));
    }

    public void show_userCard(@NonNull String title, @NonNull String content, @NonNull UserDataCallback callback) {
        MaterialDialog userData = new MaterialDialog.Builder(context)
                .title(title)
                .customView(R.layout.dialog_user_card, false)
                .positiveText("确认")
                .negativeText("取消")
                .onAny((dialog, which) -> {
                    if (which == DialogAction.POSITIVE) {
                        StringBuilder s = new StringBuilder();
                        s.append(mEt.getText());

                        callback.onPositive(s.toString());
                    } else {
                        callback.onNegative();
                    }
                })
                .show();

        mEt = (EditText) userData.findViewById(R.id.user_card);
        mEt.addTextChangedListener(new UserDataTextWatcher(mEt));
    }

    public void show_userFace(@NonNull String title, @NonNull String content, @NonNull UserDataCallback callback) {
        MaterialDialog userData = new MaterialDialog.Builder(context)
                .title(title)
                .customView(R.layout.dialog_user_face, false)
                .positiveText("确认")
                .negativeText("取消")
                .onAny((dialog, which) -> {
                    if (which == DialogAction.POSITIVE) {
                        StringBuilder s = new StringBuilder();
                        s.append(mEt.getText());

                        callback.onPositive(s.toString());
                    } else {
                        callback.onNegative();
                    }
                })
                .show();

        mEt = (EditText) userData.findViewById(R.id.user_face);
        mEt.addTextChangedListener(new UserDataTextWatcher(mEt));
    }

    public void show_userFp(@NonNull String title, @NonNull String content, @NonNull UserDataCallback callback) {
        MaterialDialog userData = new MaterialDialog.Builder(context)
                .title(title)
                .customView(R.layout.dialog_user_fp, false)
                .positiveText("确认")
                .negativeText("取消")
                .onAny((dialog, which) -> {
                    if (which == DialogAction.POSITIVE) {
                        StringBuilder s = new StringBuilder();
                        s.append(mEt.getText());

                        callback.onPositive(s.toString());
                    } else {
                        callback.onNegative();
                    }
                })
                .show();

        mEt = (EditText) userData.findViewById(R.id.user_fp);
        mEt.addTextChangedListener(new UserDataTextWatcher(mEt));
    }
}
