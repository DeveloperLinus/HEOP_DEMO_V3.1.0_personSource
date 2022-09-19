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

/**
 * Created by liuxu11 on 2021/3/26.
 * description:
 */
public class CardInfoShow {
    private String cardNo;
    private Integer imageDelId;

    public CardInfoShow(String cardNo, Integer imageDelId){
        this.cardNo = cardNo;
        this.imageDelId = imageDelId;
    }

    public String getCardNo(){
        return cardNo;
    }

    public Integer getImageDelId(){
        return imageDelId;
    }
}
