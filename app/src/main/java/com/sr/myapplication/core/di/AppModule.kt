package com.sr.myapplication.core.di

import com.sr.myapplication.core.app.ApiConstants
import com.sr.myapplication.core.network.DataRepository
import com.sr.myapplication.core.network.RetrofitAPIInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun webService(retrofit: Retrofit): RetrofitAPIInterface {
        return retrofit.create(RetrofitAPIInterface::class.java)
    }

    @Provides
    @Singleton
    fun retrofit(gsonConverterFactory: GsonConverterFactory?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun gsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun repository(): DataRepository {
        return DataRepository()
    }
}