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
package com.hik.app.main.access;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hik.app.BR;
import com.hik.app.R;
import com.hik.app.main.access.utils.PageTimeoutTracker;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseFragment;
import com.hik.archi.utils.KLog;
import com.hik.proto.data.event.AccessControllerEventBean;
import com.hik.proto.data.param.EventType;
import com.hik.proto.event.ApiEvent;

import java.util.Locale;

/**
 * Created by linzijian on 2021/3/3.
 */
public class AccessFragment extends BaseFragment {

    private static final String TAG = "AccessFragment";

    private AccessViewModel mState;
    private TextToSpeech textToSpeech;
    private PageTimeoutTracker pageTimeoutTracker;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(AccessViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_access, BR.vm, mState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //接收参数
//        Bundle bundle = getArguments();
//        Boolean bFaceCapture = null;
//        if (bundle != null) {
//            bFaceCapture = bundle.getBoolean("faceCapture", false);
//        }
//        KLog.e("faceCapture:" + bFaceCapture);

        pageTimeoutTracker = PageTimeoutTracker.getInstance(this, new PageTimeoutTracker.ITimeOutHandler() {
            @Override
            public void onStart() {
                mState.authenticationVisible.setValue(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                mState.authenticationVisible.setValue(View.GONE);
            }
        });

        mState.goConfig.observe(this, o -> {
            KLog.e(TAG, "action_accessFragment_to_configFragment");
            nav().navigate(R.id.action_accessFragment_to_configFragment);
        });

        textToSpeech = new TextToSpeech(requireContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int i = textToSpeech.setLanguage(Locale.CHINA);
                if ((i != TextToSpeech.LANG_AVAILABLE) && (i != TextToSpeech.LANG_COUNTRY_AVAILABLE)) {
                    KLog.e(TAG, "TTS不支持中文");
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApiEvent.INSTANCE.getAccessControllerEvent().observeInFragment(this, accessControllerEvent -> {
            if (isHidden()) {
                return;
            }
            AccessControllerEventBean access = accessControllerEvent.getAccessControllerEvent();
            if (access == null) {
                return;
            } else {
                if (access.getSubEventType() == null) {
                    return;
                }
            }
            pageTimeoutTracker.start(200);
            switch (access.getSubEventType()) {
                case EventType.MINOR_LEGAL_CARD_PASS:
                case EventType.MINOR_FINGERPRINT_COMPARE_PASS:
                case EventType.MINOR_FACE_VERIFY_PASS:
                case EventType.MINOR_REMOTE_OPEN_DOOR_PASS:
                case EventType.MINOR_PASSWD_VERIFY_PASS:
                    mState.authenticationBackground.set(requireContext().getResources().getDrawable(R.mipmap.identification_bg_success, null));
                    mState.authenticationImgResult.set(requireContext().getResources().getDrawable(R.mipmap.icon_success, null));
                    mState.authenticationResultText.set("开门成功");
                    mState.authenticationResultTipsText.set("欢迎您的到来");
                    textToSpeech.speak("谢谢", TextToSpeech.QUEUE_ADD, null, null);
                    break;
                case EventType.MINOR_FACE_VERIFY_FAIL:
                case EventType.MINOR_FACE_RECOGNIZE_FAIL:
                case EventType.MINOR_FINGERPRINT_COMPARE_FAIL:
                case EventType.MINOR_FINGERPRINT_INEXISTENCE:
                case EventType.MINOR_INVALID_CARD:
                case EventType.MINOR_CARD_NO_RIGHT:
                    mState.authenticationBackground.set(requireContext().getResources().getDrawable(R.mipmap.identification_bg_fail, null));
                    mState.authenticationImgResult.set(requireContext().getResources().getDrawable(R.mipmap.icon_failed, null));
                    mState.authenticationResultText.set("认证失败");
                    mState.authenticationResultTipsText.set("请重试或联系管理人员");
                    textToSpeech.speak("认证失败", TextToSpeech.QUEUE_ADD, null, null);
                    break;
                case EventType.MINOR_CARD_OUT_OF_DATE:
                case EventType.MINOR_CARD_INVALID_PERIOD:
                    mState.authenticationBackground.set(requireContext().getResources().getDrawable(R.mipmap.overtime_bg_fail, null));
                    mState.authenticationImgResult.set(requireContext().getResources().getDrawable(R.mipmap.icon_failed, null));
                    mState.authenticationResultText.set("信息过期");
                    mState.authenticationResultTipsText.set("请联系管理人员修改权限");
                    textToSpeech.speak("信息过期", TextToSpeech.QUEUE_ADD, null, null);
                    break;
                default:
                    mState.authenticationVisible.setValue(View.GONE);
                    pageTimeoutTracker.cancel();
                    break;
            }
        });
    }
}
