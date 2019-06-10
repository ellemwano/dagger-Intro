package com.moducode.daggerexample.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.moducode.daggerexample.room.DbRepo
import com.moducode.daggerexample.room.DbRepoImpl
import com.moducode.daggerexample.room.EpisodeDB
import com.moducode.daggerexample.schedulers.SchedulersBase
import com.moducode.daggerexample.schedulers.SchedulersImpl
import com.moducode.daggerexample.service.EpisodeService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber


@Module
class ContextModule(val context: Context) {  // context provided via constructor

    @Provides
    fun provideContext(): Context = context
}

@Module(includes = [ContextModule::class])
class RetrofitModule {

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, moshi: MoshiConverterFactory, callAdapterFactory: RxJava2CallAdapterFactory): Retrofit =
            Retrofit.Builder()
                    .client(httpClient)
                    .addCallAdapterFactory(callAdapterFactory)
                    .baseUrl("http://api.tvmaze.com")
                    .addConverterFactory(moshi)
                    .build()

    @Provides
    fun provideHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient =
            OkHttpClient.Builder()
                    .addNetworkInterceptor(interceptor)
                    .cache(cache)
                    .build()

    @Provides
    fun provideInterceptor(): Interceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Timber.d(it) })
                    .apply { level = HttpLoggingInterceptor.Level.BASIC }

    @Provides
    fun provideCache(context: Context): Cache = Cache(context.cacheDir, 5*5*1024)

    @Provides
    fun provideRxCallAdapter(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    fun provideMoshi(): MoshiConverterFactory =
            MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
}

@Module(includes = [RetrofitModule::class])
class EpisodeServiceModule {

    @Provides
    fun provideEpisodeService(retrofit: Retrofit): EpisodeService =
            retrofit.create(EpisodeService::class.java)
}

@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): DbRepo =
            DbRepoImpl(Room.databaseBuilder(context, EpisodeDB::class.java, "db-episodes").build())
}

@Module
class SchedulerModule {

    @Provides
    fun provideSchedulers(): SchedulersBase = SchedulersImpl()
}