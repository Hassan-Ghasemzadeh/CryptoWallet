package com.softwarecleandevelopment.core.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    @CryptoInfoRetrofit
    fun provideCryptoInfoRetrofit(gson: Gson): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        return Retrofit.Builder().baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client).build()
    }

    @Provides
    @Singleton
    @BlockCypherRetrofit
    fun provideBlockCypherRetrofit(gson: Gson): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        return Retrofit.Builder().baseUrl("https://api.blockcypher.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client).build()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CryptoInfoRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BlockCypherRetrofit
