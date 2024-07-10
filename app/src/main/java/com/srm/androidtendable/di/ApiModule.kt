package com.srm.androidtendable.di


import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.srm.androidtendable.api.ApiServices
import com.srm.androidtendable.utils.Constants.BASE_URL
import com.srm.androidtendable.utils.Constants.NETWORK_TIMEOUT

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Timeout
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val baseUrl = BASE_URL
const val networkTime = NETWORK_TIMEOUT

fun provideGson(): Gson = GsonBuilder().setLenient().create()

fun provideOkHttpClient() = OkHttpClient.Builder().build()

fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): ApiServices =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiServices::class.java)

val apiModule = module {
    single { baseUrl }
    single { networkTime }
    single { provideGson() }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get(), get()) }
}