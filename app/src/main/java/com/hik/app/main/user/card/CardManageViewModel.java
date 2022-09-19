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
package com.hik.app.main.user.card;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hik.app.R;
import com.hik.archi.ui.adapter.binding.BindingCommand;
import com.hik.archi.utils.KLog;
import com.hik.proto.ApiHelper;
import com.hik.proto.data.bean.user.card.CardData;
import com.hik.proto.data.bean.user.card.CardInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liuxu11
 * @describe:
 * @date on 2021/3/15 18:02
 **/
public class CardManageViewModel extends ViewModel {
    private static final String TAG = "CardManageViewModel";

    MutableLiveData<Boolean> go_back = new MutableLiveData<>();
    MutableLiveData<Boolean> pAddCard = new MutableLiveData<>();

    //列表
    List<CardInfoShow> cardList = new ArrayList<>();

    public BindingCommand cancel_config = new BindingCommand(() -> go_back.setValue(false));
    public BindingCommand add_fp = new BindingCommand(() -> pAddCard.setValue(true));

    /**
     * 获取卡片信息
     */
    public void getCardData() {
        Object o = ApiHelper.INSTANCE.getCardData();
        if (o instanceof CardData) {
            KLog.v(TAG, "=== getCardData ok");

            if (null == (((CardData) o).getCardInfoSearch()))
            {
                KLog.v(TAG, "=== CardInfoSearch is null");
                return;
            }

            if (null == (Objects.requireNonNull(((CardData) o).getCardInfoSearch())).getCardInfo())
            {
                KLog.v(TAG, "=== CardInfo is null");
                return;
            }

            for (CardInfo cardInfo : Objects.requireNonNull(Objects.requireNonNull(((CardData) o).getCardInfoSearch()).getCardInfo())) {
                cardList.add(new CardInfoShow(
                        cardInfo.getCardNo(),
                        R.mipmap.list_icon_delete));
            }
        } else {
            KLog.e(TAG, "getFpData onError");
        }
    }

}
