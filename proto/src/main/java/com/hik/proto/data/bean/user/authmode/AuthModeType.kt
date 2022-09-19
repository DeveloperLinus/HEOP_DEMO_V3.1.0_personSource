package com.hik.proto.data.bean.user.authmode;

/**
 * Created by liuxu11 on 2021/3/31.
 * description:
 */
enum class AuthModeType {
    normal_auth,       //普通认证
    disable_auth,      //关闭认证，
    qr_auth,           //二维码认证，
    admin_auth,        //管理员认证，
    enroll,            //录入模式
    low_power,         //低功耗模式
    invalid            //无效模式
}