package com.lmkhant.foodrecipedb.di

import android.content.Context
import com.lmkhant.foodrecipedb.common.network.NetworkConnectivityInterceptor
import com.lmkhant.foodrecipedb.common.network.NetworkConnectivityInterceptorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkConnectionProvider {

    @Provides
    @Singleton
    fun provideNetworkConnectivityInterceptor(@ApplicationContext context: Context): NetworkConnectivityInterceptor
    = NetworkConnectivityInterceptorImpl(context)
}