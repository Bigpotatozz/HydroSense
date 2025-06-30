package com.oscar.hydrosense.core.di

import com.oscar.hydrosense.login.data.network.LoginClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5114/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    };

    @Singleton
    @Provides
    fun provideLoginClient(retrofit: Retrofit): LoginClient{
        return retrofit.create(LoginClient::class.java);

    }
}