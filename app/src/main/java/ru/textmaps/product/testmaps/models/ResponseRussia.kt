package ru.textmaps.product.testmaps.models


import com.google.gson.annotations.SerializedName

data class ResponseRussia(
    @SerializedName("features")
    val features: List<Feature?>?,
    @SerializedName("type")
    val type: String?
) {
    data class Feature(
        @SerializedName("geometry")
        val geometry: Geometry?,
        @SerializedName("type")
        val type: String?
    ) {
        data class Geometry (
            @SerializedName("coordinates")
            val coordinates: List<List<List<List<Double?>?>?>?>?,
            @SerializedName("type")
            val type: String?
        )
    }
}