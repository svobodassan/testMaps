package ru.textmaps.product.testmaps

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun OkHttpClient.Builder.addLoggingWhenDebug(): OkHttpClient.Builder {

    val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return addInterceptor(interceptor)
}