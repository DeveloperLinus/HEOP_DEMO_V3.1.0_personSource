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
package com.hik.app.init;

import com.hik.app.BuildConfig;
import com.hik.app.main.MainActivity;
import com.hik.app.R;
import com.hik.app.model.Repository;
import com.hik.archi.init.BaseApplication;
import com.hik.archi.crash.CaocConfig;
import com.hik.archi.utils.KLog;
import com.hik.archi.utils.Utils;
import com.hik.proto.base.ApiInit;

/**
 * Created by linzijian on 2021/3/2.
 * 需要为项目准备一个Application来继承BaseApplication,
 * 以便需要时在Activity/Fragment中享有Application级作用域的Callback-ViewModel.
 */
public class initApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        //初始化Api通讯库
        ApiInit.initSocketJna(null, null);
        //初始化数据库
        Repository.init(this);
        //初始化日志
        KLog.init(BuildConfig.LOG_DEBUG, "HiHEOP");
        //初始化全局异常崩溃
        initCrash();
    }

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)              //背景模式,开启沉浸式
                .enabled(true)                                                  //是否启动全局异常捕获
                .showErrorDetails(true)                                         //是否显示错误详细信息
                .showRestartButton(true)                                        //是否显示重启按钮
                .trackActivities(true)                                          //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000)                                  //崩溃的间隔时间(毫秒)
                .errorDrawable(R.drawable.customactivityoncrash_error_image)    //错误图标
                .restartActivity(MainActivity.class)                            //重新启动后的activity
                .apply();
    }
}
