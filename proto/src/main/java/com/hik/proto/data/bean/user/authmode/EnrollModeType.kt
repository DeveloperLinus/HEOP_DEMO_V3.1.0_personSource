package com.hik.proto.data.bean.user.authmode;

/**
 * Created by liuxu11 on 2021/3/31.
 * description:
 */
enum class EnrollModeType {
    unknow,      //未知类型
    face,        //人脸录入
    card,        //卡片录入
    finger,      //指纹录入
    invalid
}