package com.green.comma

import android.app.Application
import com.green.comma.util.PreferenceUtil

class CommaApplication: Application() {
    companion object{
        lateinit var preferences: PreferenceUtil
    }

    override fun onCreate() {
        preferences = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}