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
package com.hik.proto.callback

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * Created by linzijian on 2021/3/3.
 * UnPeekLiveData的存在是为了在"重回二级页面"的场景下,解决"数据倒灌"的问题.
 */
class UnPeekLiveData<T> : ProtectedUnPeekLiveData<T>() {
    public override fun setValue(value: T?) {
        super.setValue(value)
    }

    public override fun postValue(value: T?) {
        super.postValue(value)
    }

    /**
     * 请不要在UnPeekLiveData中使用observe方法.
     * 取而代之的是在Activity和fragment中分别使用observeInActivity和observeInFragment来观察.
     */
    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        throw IllegalArgumentException("请不要在 UnPeekLiveData 中使用 observe 方法。" +
                "取而代之的是在 Activity 和 Fragment 中分别使用 observeInActivity 和 observeInFragment 来观察。\n\n" +
                "Taking into account the normal permission of preventing backflow logic, " +
                " do not use observeForever to communicate between pages." +
                "Instead, you can use ObserveInActivity and ObserveInFragment methods " +
                "to observe in Activity and Fragment respectively.")
    }

    /**
     * 请不要在UnPeekLiveData中使用observeForever方法.
     */
    override fun observeForever(observer: Observer<in T?>) {
        throw IllegalArgumentException("出于生命周期安全的考虑，请不要在 UnPeekLiveData 中使用 observeForever 方法。\n\n" +
                "Considering avoid lifecycle security issues," +
                " do not use observeForever for communication between pages.")
    }

    class Builder<T> {
        /**
         * 是否允许传入 null value
         */
        private var isAllowNullValue = false
        fun setAllowNullValue(allowNullValue: Boolean): Builder<T> {
            isAllowNullValue = allowNullValue
            return this
        }

        fun create(): UnPeekLiveData<T> {
            val liveData: UnPeekLiveData<T> = UnPeekLiveData()
            liveData.isAllowNullValue = isAllowNullValue
            return liveData
        }
    }
}