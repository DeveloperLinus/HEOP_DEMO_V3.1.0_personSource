package com.hik.app.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.library.baseAdapters.BR;

import android.content.Intent;
import android.os.Bundle;

import com.hik.app.R;
import com.hik.app.activate.ActivateActivity;
import com.hik.app.main.MainActivity;
import com.hik.app.main.MainViewModel;
import com.hik.archi.ui.databinding.DataBindingConfig;
import com.hik.archi.ui.page.BaseActivity;
import com.hik.proto.event.ApiListener;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/26 15:46
 **/
public class UserActivity extends BaseActivity {

    private UserViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(UserViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_user, BR.vm, mState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}