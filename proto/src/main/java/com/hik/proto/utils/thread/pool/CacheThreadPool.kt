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
package com.hik.proto.utils.thread.pool

import java.util.concurrent.Future
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by linzijian on 2021/2/21.
 * 无核心线程，最大数量工作线程，存活时间短
 * 可用于一次性、大数量、高频等异步任务处理
 */
class CacheThreadPool {
    @Volatile
    private var mExecutors: ThreadPoolExecutor? = null
    private fun initThreadPoolExecutor() {
        if (mExecutors == null || mExecutors!!.isShutdown || mExecutors!!.isTerminated) {
            synchronized(CacheThreadPool::class.java) {
                if (mExecutors == null || mExecutors!!.isShutdown || mExecutors!!.isTerminated) {
                    val keepAliveTime = 60L
                    val unit = TimeUnit.MILLISECONDS
                    mExecutors = ThreadPoolExecutor(
                        0, Int.MAX_VALUE, keepAliveTime, unit, SynchronousQueue()
                    )
                }
            }
        }
    }

    fun execute(task: Runnable?) {
        initThreadPoolExecutor()
        mExecutors!!.execute(task)
    }

    fun submit(task: Runnable?): Future<*> {
        initThreadPoolExecutor()
        return mExecutors!!.submit(task)
    }

    fun remove(task: Runnable?) {
        initThreadPoolExecutor()
        mExecutors!!.remove(task)
    }
}
