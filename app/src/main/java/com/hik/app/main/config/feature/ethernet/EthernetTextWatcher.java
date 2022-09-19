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

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by linzijian on 2021/3/9.
 */
public class EthernetTextWatcher implements TextWatcher {

    private EditText et;
    private ArrayList<EditText> mList;

    EthernetTextWatcher(ArrayList<EditText> mList, EditText et) {
        this.et = et;
        this.mList  = mList;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 3){
            if (mList.get(0).equals(this.et)) {
                mList.get(1).requestFocus();
            } else if (mList.get(1).equals(this.et)) {
                mList.get(2).requestFocus();
            } else if (mList.get(2).equals(this.et)) {
                mList.get(3).requestFocus();
            }
        } else if (s.length() == 0) {
            if (mList.get(3).equals(this.et)) {
                mList.get(2).requestFocus();
            } else if (mList.get(2).equals(this.et)) {
                mList.get(1).requestFocus();
            } else if (mList.get(1).equals(this.et)) {
                mList.get(0).requestFocus();
            }
        }
        if (s.length() > 0) {
            if (Integer.parseInt(String.valueOf(s)) > 255) {
                s.delete(s.length() - 1, s.length());
            }
        }
    }
}
