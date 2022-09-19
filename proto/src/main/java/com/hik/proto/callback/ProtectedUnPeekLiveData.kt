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

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStore
import java.util.*

/**
 * Created by linzijian on 2021/3/3.
 * 用于限制从Activity/Fragment篡改来自"数据层"的数据,数据层的数据务必通过"唯一可信源"来分发.
 */
open class ProtectedUnPeekLiveData<T> : LiveData<T?>() {
    @JvmField
    protected var isAllowNullValue = false
    private val observers = HashMap<Int, Boolean?>()

    /**
     * 适合在 activity 中使用的 observe UnPeek 方法
     * A Observe UnPeek method which suitable for use in an activity
     */
    fun observeInActivity(activity: AppCompatActivity, observer: Observer<in T?>) {
        val storeId = System.identityHashCode(activity.viewModelStore)
        observe(storeId, activity, observer)
    }

    /**
     * 适合在 fragment 中使用的 observe UnPeek 方法
     * A Observe UnPeek method which suitable for use in an fragment
     */
    fun observeInFragment(fragment: Fragment, observer: Observer<in T?>) {
        val owner = fragment.viewLifecycleOwner
        val storeId = System.identityHashCode(fragment.viewModelStore)
        observe(storeId, owner, observer)
    }

    /**
     * 通用的 observe UnPeek 方法
     * A universal Observe UnPeek method
     */
    fun observeUnPeek(owner: LifecycleOwner, store: ViewModelStore, observer: Observer<in T?>) {
        val storeId = System.identityHashCode(store)
        observe(storeId, owner, observer)
    }

    private fun observe(
        storeId: Int,
        owner: LifecycleOwner,
        observer: Observer<in T?>
    ) {
        if (observers[storeId] == null) {
            observers[storeId] = true
        }
        super.observe(owner, Observer { t: T? ->
            if (!observers[storeId]!!) {
                observers[storeId] = true
                if (t != null || isAllowNullValue) {
                    observer.onChanged(t)
                }
            }
        })
    }

    /**
     * 重写的 setValue 方法，默认不接收 null
     * 可通过 Builder 配置允许接收
     * 可通过 Builder 配置消息延时清理的时间
     * override setValue, do not receive null by default
     * You can configure to allow receiving through Builder
     * And also, You can configure the delay time of message clearing through Builder
     */
    override fun setValue(value: T?) {
        if (value != null || isAllowNullValue) {
            for (entry in observers.entries) {
                entry.setValue(false)
            }
            super.setValue(value)
        }
    }

    protected fun clear() {
        super.setValue(null)
    }
}