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
package com.hik.proto.utils.thread

import com.hik.proto.utils.thread.pool.CacheThreadPool
import com.hik.proto.utils.thread.pool.DefaultThreadPool
import com.hik.proto.utils.thread.pool.SingleThreadPool
import java.util.concurrent.ThreadPoolExecutor

/**
 * Created by linzijian on 2021/2/21.
 * 线程池代理，根据不同需求返回不同类型线程池
 */
object ThreadPoolProxyFactory {
    @Volatile
    var defaultThreadPool: DefaultThreadPool? = null
        get() {
            if (field == null) {
                synchronized(ThreadPoolProxyFactory::class.java) {
                    if (field == null) {
                        field = DefaultThreadPool(4, 16)
                    }
                }
            }
            return field
        }
        private set

    @Volatile
    var cacheThreadPool: CacheThreadPool? = null
        get() {
            if (field == null) {
                synchronized(ThreadPoolProxyFactory::class.java) {
                    if (field == null) {
                        field = CacheThreadPool()
                    }
                }
            }
            return field
        }
        private set

    @Volatile
    var singleThreadPool: SingleThreadPool? = null
        get() {
            if (field == null) {
                synchronized(ThreadPoolExecutor::class.java) {
                    if (field == null) {
                        field = SingleThreadPool()
                    }
                }
            }
            return field
        }
        private set
}
