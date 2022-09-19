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
package com.hik.archi.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by linzijian on 2021/3/2.
 * Desc: utils about adapt screen
 */
@SuppressWarnings("unused")
public final class AdaptScreenUtils {

    private static boolean isInitMiui = false;
    private static Field mTmpMetricsField;

    /**
     * Adapt for the horizontal screen, and call it in [android.app.Activity.getResources].
     */
    public static Resources adaptWidth(Resources resources, int designWidth) {
        DisplayMetrics dm = getDisplayMetrics(resources);
        float newXdpi = dm.xdpi = (dm.widthPixels * 72f) / designWidth;
        setAppDmXdpi(newXdpi);
        return resources;
    }

    /**
     * Adapt for the vertical screen, and call it in [android.app.Activity.getResources].
     */
    public static Resources adaptHeight(Resources resources, int designHeight) {
        DisplayMetrics dm = getDisplayMetrics(resources);
        float newXdpi = dm.xdpi = (dm.heightPixels * 72f) / designHeight;
        setAppDmXdpi(newXdpi);
        return resources;
    }

    /**
     * @param resources The resources.
     * @return the resource
     */
    public static Resources closeAdapt(Resources resources) {
        DisplayMetrics dm = getDisplayMetrics(resources);
        float newXdpi = dm.xdpi = dm.density * 72;
        setAppDmXdpi(newXdpi);
        return resources;
    }

    /**
     * Value of pt to value of px.
     *
     * @param ptValue The value of pt.
     * @return value of px
     */
    public static int pt2Px(float ptValue) {
        DisplayMetrics metrics = Utils.getApp().getResources().getDisplayMetrics();
        return (int) (ptValue * metrics.xdpi / 72f + 0.5);
    }

    /**
     * Value of px to value of pt.
     *
     * @param pxValue The value of px.
     * @return value of pt
     */
    public static int px2Pt(float pxValue) {
        DisplayMetrics metrics = Utils.getApp().getResources().getDisplayMetrics();
        return (int) (pxValue * 72 / metrics.xdpi + 0.5);
    }

    private static void setAppDmXdpi(final float xdpi) {
        Utils.getApp().getResources().getDisplayMetrics().xdpi = xdpi;
    }

    private static DisplayMetrics getDisplayMetrics(Resources resources) {
        DisplayMetrics miuiDisplayMetrics = getMiuiTmpMetrics(resources);
        if (miuiDisplayMetrics == null) {
            return resources.getDisplayMetrics();
        }
        return miuiDisplayMetrics;
    }

    private static DisplayMetrics getMiuiTmpMetrics(Resources resources) {
        if (!isInitMiui) {
            DisplayMetrics ret = null;
            String simpleName = resources.getClass().getSimpleName();
            if ("MiuiResources".equals(simpleName) || "XResources".equals(simpleName)) {
                try {
                    //noinspection JavaReflectionMemberAccess
                    mTmpMetricsField = Resources.class.getDeclaredField("mTmpMetrics");
                    mTmpMetricsField.setAccessible(true);
                    ret = (DisplayMetrics) mTmpMetricsField.get(resources);
                } catch (Exception e) {
                    Log.e("AdaptScreenUtils", "no field of mTmpMetrics in resources.");
                }
            }
            isInitMiui = true;
            return ret;
        }
        if (mTmpMetricsField == null) {
            return null;
        }
        try {
            return (DisplayMetrics) mTmpMetricsField.get(resources);
        } catch (Exception e) {
            return null;
        }
    }
}
