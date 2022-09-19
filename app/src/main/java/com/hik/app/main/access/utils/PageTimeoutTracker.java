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
package com.hik.app.main.access.utils;

import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by linzijian on 2021/1/25.
 */
public class PageTimeoutTracker implements Runnable, LifecycleObserver {

    private volatile static PageTimeoutTracker instance;
    private int time;
    private final ITimeOutHandler iTimeOutHandler;
    private final Handler handler;
    private boolean isRun;

    private PageTimeoutTracker(LifecycleOwner lifecycleOwner, ITimeOutHandler iTimeOutHandler) {
        handler = new Handler();
        lifecycleOwner.getLifecycle().addObserver(this);
        this.iTimeOutHandler = iTimeOutHandler;
    }

    public static PageTimeoutTracker getInstance(LifecycleOwner lifecycleOwner, ITimeOutHandler iTimeOutHandler) {
        if (instance == null) {
            synchronized (PageTimeoutTracker.class) {
                if (instance == null) {
                    instance = new PageTimeoutTracker(lifecycleOwner, iTimeOutHandler);
                }
            }
        }
        return instance;
    }

    @Override
    public void run() {
        if (isRun) {
            if (time == 0) {
                cancel();
            } else {
                time --;
                handler.postDelayed(this, 10);
            }
        }
    }

    public void start() {
        isRun = true;
        this.time = 500;
        if (iTimeOutHandler != null) {
            iTimeOutHandler.onStart();
        }
        handler.post(this);
    }

    public void start(int time) {
        isRun = true;
        this.time = time;
        if (iTimeOutHandler != null) {
            iTimeOutHandler.onStart();
        }
        handler.post(this);
    }

    public void cancel() {
        isRun = false;
        this.time = 0;
        if (iTimeOutHandler != null) {
            iTimeOutHandler.onFinish();
        }
        handler.removeCallbacks(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        cancel();
    }

    public interface ITimeOutHandler {
        void onStart();
        void onFinish();
    }
}
