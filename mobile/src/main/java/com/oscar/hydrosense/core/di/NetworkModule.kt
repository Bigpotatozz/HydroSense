package com.oscar.hydrosense.core.di

import com.oscar.hydrosense.account.data.network.AccountClient
import com.oscar.hydrosense.login.data.network.LoginClient
import com.oscar.hydrosense.registro.data.network.RegisterClient
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
            .baseUrl("http://192.168.1.25:5114/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    };

    @Singleton
    @Provides
    fun provideLoginClient(retrofit: Retrofit): LoginClient{
        return retrofit.create(LoginClient::class.java);

    }

    @Singleton
    @Provides
    fun provideRegisterClient(retrofit: Retrofit): RegisterClient{
        return retrofit.create(RegisterClient::class.java)
    }


    @Singleton
    @Provides
    fun provideAccountClient(retrofit: Retrofit): AccountClient{
        return retrofit.create(AccountClient::class.java)
    }
}