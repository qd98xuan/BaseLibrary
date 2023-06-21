package com.hx.baselibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hx.baselibrary.exception.AppCrashHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCrashHandler.getInstance(this)
        var s: String? = null
        s!!.toString()
    }
}