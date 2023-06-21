package com.hx.baselibrary.application

import android.app.Application
import com.hx.baselibrary.exception.AppCrashHandler
import com.tencent.mmkv.MMKV

/**
 * 基础的application类
 */
class BaseApplication:Application() {
    init {
        AppCrashHandler.getInstance(applicationContext)
        MMKV.initialize(applicationContext)
    }

}