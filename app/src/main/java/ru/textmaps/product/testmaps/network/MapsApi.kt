package ru.textmaps.product.testmaps.network

import retrofit2.http.GET
import ru.textmaps.product.testmaps.models.ResponseRussia

interface MapsApi {
    @GET("api/russia.geo.json")
    suspend fun getRussiaGeo(): ResponseRussia
}