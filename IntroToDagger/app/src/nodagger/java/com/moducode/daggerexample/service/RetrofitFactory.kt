package com.moducode.daggerexample.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.File

class RetrofitFactory {

    companion object {

        private const val CACHE_SIZE: Long = 5 * 1024 * 1024
        private const val BASE_URL: String = "http://api.tvmaze.com/"

        fun <T> create(cacheFile: File, clazz: Class<T>): T {
            val interceptor = HttpLoggingInterceptor(
                    HttpLoggingInterceptor.Logger { Timber.d(it) }
            )
            interceptor.level = HttpLoggingInterceptor.Level.BASIC

            val cache = Cache(cacheFile, CACHE_SIZE)

            val client = OkHttpClient
                    .Builder()
                    .addInterceptor(interceptor)
                    .cache(cache)
                    .build()

            val retrofit = Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(clazz)
        }

    }

}