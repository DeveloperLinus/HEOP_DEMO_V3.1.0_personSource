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

package com.hik.app.main.user.adapter;

/**
 * Created by liuxu11 on 2021/3/26.
 * description:
 */
public class UserInfoShow {
    private String name;
    private String employeeNo;
    private Integer imageFaceId;
    private Integer imageFpId;
    private Integer imageCardId;
    private Integer imageArrowRightId;

    public UserInfoShow(String employeeNo, String name, Integer imageFaceId, Integer imageFpId, Integer imageCardId, Integer imageArrowRightId){
        this.employeeNo = employeeNo;
        this.name = name;
        this.imageFaceId = imageFaceId;
        this.imageFpId = imageFpId;
        this.imageCardId = imageCardId;
        this.imageArrowRightId = imageArrowRightId;
    }

    public String getEmployeeNo(){
        return employeeNo;
    }

    public String getName(){
        return name;
    }

    public Integer getImageFaceId(){
        return imageFaceId;
    }

    public Integer getImageFpId(){
        return imageFpId;
    }

    public Integer getImageCardId(){
        return imageCardId;
    }

    public Integer getImageArrowRightId(){
        return imageArrowRightId;
    }
}
