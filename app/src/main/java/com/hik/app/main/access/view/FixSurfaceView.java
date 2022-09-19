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
package com.hik.app.main.access.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hik.archi.utils.KLog;
import com.hik.common.HiAcs;
import com.hik.common.HiAcsCommon;

/**
 * Created by linzijian on 2021/3/3.
 */
public class FixSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "FixSurfaceView";
    private static Boolean FixSurfaceFlg = false;

    public FixSurfaceView(Context context) {
        super(context);
        init();
    }

    public FixSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FixSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FixSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        KLog.e(TAG,"=== init");

        if (FixSurfaceFlg) {
            KLog.e(TAG, "=== init " + true);
            surfaceDestroyed(this.getHolder());
        } else {
            KLog.e(TAG, "=== init " + false);
            FixSurfaceFlg = true;
        }

        this.setZOrderOnTop(true);
        this.setZOrderMediaOverlay(true);
        this.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        KLog.e(TAG,"=== surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        KLog.e(TAG,"=== surfaceChanged");

        HiAcs.getInstance().setPreviewSurface(0, 0, width, height, holder.getSurface());
        HiAcs.getInstance().openCapt(0);
        HiAcs.getInstance().setSurfaceCaptData(0, 0, 0);
        HiAcs.getInstance().setSurfaceMirror(0, 0, HiAcsCommon.MirrorMode.MIRROR_MODE_HORIZONTAL);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        KLog.e(TAG,"=== surfaceDestroyed");
        this.getHolder().removeCallback(this);

        HiAcs.getInstance().cancelPreviewSurface(0, 0);
    }
}