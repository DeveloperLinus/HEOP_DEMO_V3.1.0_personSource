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

import java.util.concurrent.*

/**
 * Created by linzijian on 2021/2/21.
 * 指定数量的核心线程和工作线程5秒存活时间
 * 可用于一般异步任务处理
 */
class DefaultThreadPool(private val mCoreSize: Int, private val mMaximumSize: Int) {
    @Volatile
    private var mExecutor: ThreadPoolExecutor? = null
    private fun initThreadPoolExecutor() {
        if (mExecutor == null || mExecutor!!.isShutdown || mExecutor!!.isTerminated) {
            synchronized(DefaultThreadPool::class.java) {
                if (mExecutor == null || mExecutor!!.isShutdown || mExecutor!!.isTerminated) {
                    val keepAliveTime: Long = 5000
                    val unit = TimeUnit.MILLISECONDS
                    val blockingQueue: BlockingQueue<Runnable> = LinkedBlockingDeque()
                    val threadFactory = Executors.defaultThreadFactory()
                    val handler: RejectedExecutionHandler = ThreadPoolExecutor.DiscardPolicy()
                    mExecutor = ThreadPoolExecutor(
                        mCoreSize,
                        mMaximumSize, keepAliveTime,
                        unit, blockingQueue, threadFactory, handler
                    )
                }
            }
        }
    }

    fun execute(task: Runnable?) {
        initThreadPoolExecutor()
        mExecutor!!.execute(task)
    }

    fun submit(task: Runnable?): Future<*> {
        initThreadPoolExecutor()
        return mExecutor!!.submit(task)
    }

    fun remove(task: Runnable?) {
        initThreadPoolExecutor()
        mExecutor!!.remove(task)
    }
}
