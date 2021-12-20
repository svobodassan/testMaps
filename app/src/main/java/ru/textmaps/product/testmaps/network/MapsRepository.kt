package ru.textmaps.product.testmaps.network

import ru.textmaps.product.testmaps.models.ResponseRussia
import javax.inject.Inject

class MapsRepository @Inject constructor() {
    suspend fun getRussia(): ResponseRussia {
        return ApiProviderMaps().mapsApi.getRussiaGeo()
    }
}