package ru.textmaps.product.testmaps.network

class ApiProviderMaps {
        val mapsApi by lazy { retrofit.create(MapsApi::class.java) }
        private val retrofit by lazy {RetrofitMaps().retrofit}
}