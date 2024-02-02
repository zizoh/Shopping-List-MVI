package com.zizohanto.android.tobuy

import android.app.Application
import com.zizohanto.android.tobuy.di.appModule
import com.zizohanto.android.tobuy.di.cacheModule
import com.zizohanto.android.tobuy.di.dataModule
import com.zizohanto.android.tobuy.di.executorModule
import com.zizohanto.android.tobuy.di.mapperModule
import com.zizohanto.android.tobuy.di.productModule
import com.zizohanto.android.tobuy.di.shoppingListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ApplicationClass)
            modules(appModule)
            modules(cacheModule)
            modules(dataModule)
            modules(executorModule)
            modules(mapperModule)
            modules(productModule)
            modules(shoppingListModule)
        }
    }
}