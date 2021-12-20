package ru.textmaps.product.testmaps

import air.ru.obi.mobile.core.dagger.DaggerMapsDaggerComponent
import air.ru.obi.mobile.core.dagger.MapsDaggerComponent
import android.app.Application

class MyApp: Application() {

    companion object {
        lateinit var mapsDaggerComponent: MapsDaggerComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        mapsDaggerComponent = DaggerMapsDaggerComponent.builder()
            .build()
    }

}