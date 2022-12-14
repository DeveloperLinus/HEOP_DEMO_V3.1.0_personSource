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
package com.hik.archi.utils.SPUtils;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by linzijian on 2021/3/5.
 */
public interface EnhancedEditor extends SharedPreferences.Editor {

    EnhancedEditor putSerializable(String key, @Nullable Serializable value);
}
