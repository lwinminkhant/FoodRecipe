package com.lmkhant.foodrecipedb.di

import com.lmkhant.foodrecipedb.api.FoodReceiptApiService
import com.lmkhant.foodrecipedb.repository.FoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvider{

    @Provides
    @Singleton
    fun provideFoodRepository(apiService: FoodReceiptApiService): FoodRepository
    = FoodRepository(apiService)
}