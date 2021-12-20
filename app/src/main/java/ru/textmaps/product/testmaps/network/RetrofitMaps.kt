package ru.textmaps.product.testmaps.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.textmaps.product.testmaps.addLoggingWhenDebug

class RetrofitMaps {

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return try {
            OkHttpClient.Builder()
                .addLoggingWhenDebug()
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private val baseUrl: String by lazy {
        "https://waadsu.com/"
    }
}