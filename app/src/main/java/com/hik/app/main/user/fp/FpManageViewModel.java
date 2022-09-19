/*
 * Copyright 2020-present hikvision
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain user_icon_bg copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hik.app.main.user.fp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.app.R;
import com.hik.archi.ui.adapter.binding.BindingCommand;
import com.hik.archi.utils.KLog;
import com.hik.proto.ApiHelper;
import com.hik.proto.data.bean.user.fp.FingerPrintList;
import com.hik.proto.data.bean.user.fp.FpData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/15 18:02
 **/
public class FpManageViewModel extends ViewModel {
    private static final String TAG = "FpManageViewModel";

    MutableLiveData<Boolean> go_back = new MutableLiveData<>();
    MutableLiveData<Boolean> pAddfp = new MutableLiveData<>();

    //指纹列表
    List<FpInfoShow> fpList = new ArrayList<>();

    public BindingCommand cancel_config = new BindingCommand(() -> go_back.setValue(false));
    public BindingCommand add_fp = new BindingCommand(() -> pAddfp.setValue(true));

    /**
     * 获取指纹信息
     */
    public void getFpData() {
        Object o = ApiHelper.INSTANCE.getFpData();
        if (o instanceof FpData) {
            KLog.v(TAG, "=== getFpData ok");

            if (null == (((FpData) o).getFingerPrintInfo()))
            {
                KLog.v(TAG, "=== FingerPrintInfo is null");
                return;
            }

            if (null == (Objects.requireNonNull(((FpData) o).getFingerPrintInfo())).getFingerPrintList())
            {
                KLog.v(TAG, "=== FingerPrintList is null");
                return;
            }

            for (FingerPrintList fpInfo : Objects.requireNonNull(Objects.requireNonNull(((FpData) o).getFingerPrintInfo()).getFingerPrintList())) {
                fpList.add(new FpInfoShow(
                        "指纹"+fpInfo.getFingerPrintID(),
                        R.mipmap.list_icon_delete));
            }
        } else {
            KLog.e(TAG, "getFpData onError");
        }
    }

}
