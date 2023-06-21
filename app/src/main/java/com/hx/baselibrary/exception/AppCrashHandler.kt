package com.hx.baselibrary.exception

import android.content.Context
import android.os.Looper
import android.widget.Toast
import com.orhanobut.logger.Logger

/**
 * 全局异常捕获
 */
class AppCrashHandler(context: Context) : Thread.UncaughtExceptionHandler {
    private var context = context

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Logger.d("thread name:${t.name},throw error ${e.message}")
        Thread(Runnable {
            Looper.prepare()
            Toast.makeText(context, "抱歉，程序出现异常即将退出", Toast.LENGTH_LONG).show()
            Looper.loop()
        }).start()
    }

    companion object {
        var context:Context? = null
        val instance: AppCrashHandler by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            AppCrashHandler(context!!)
        }
        fun getInstance(context: Context){
            this.context = context
            instance
        }
    }
}