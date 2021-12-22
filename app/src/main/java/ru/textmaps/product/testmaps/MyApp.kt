package ru.textmaps.product.testmaps


import android.app.Application
import ru.textmaps.product.testmaps.dagger.DaggerMapsDaggerComponent
import ru.textmaps.product.testmaps.dagger.MapsDaggerComponent

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