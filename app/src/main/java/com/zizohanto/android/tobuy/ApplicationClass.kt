package com.zizohanto.android.tobuy

import android.app.Application
import com.zizohanto.android.tobuy.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ApplicationClass)
            commonModule()
        }
    }
}