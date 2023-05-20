package com.lmkhant.foodrecipedb.repository

import com.lmkhant.foodrecipedb.api.FoodReceiptApiService
import com.lmkhant.foodrecipedb.model.CategoryResponse
import com.lmkhant.foodrecipedb.model.FilterResponse
import com.lmkhant.foodrecipedb.model.MealResponse
import retrofit2.Response
import javax.inject.Inject


class FoodRepository @Inject constructor(private val apiService: FoodReceiptApiService) {

    suspend fun getCategories(): Response<CategoryResponse> {
        return apiService.getCategories()
    }

    suspend fun getAreaList(): Response<FilterResponse> {
        return apiService.getAreaList()
    }

    suspend fun getIngredientList(): Response<FilterResponse> {
        return apiService.getIngredientList()
    }

    suspend fun getFilterByCategory(category:String): Response<FilterResponse> {
        return apiService.getFilterByCategory(category)
    }

    suspend fun getFilterByArea(area: String): Response<FilterResponse> {
        return apiService.getFilterByArea(area)
    }

    suspend fun getFilterByIngredient(ingredient: String): Response<FilterResponse> {
        return apiService.getFilterByIngredient(ingredient)
    }


    suspend fun search(searchQuery:String): Response<FilterResponse>{
        return apiService.search(searchQuery)
    }

    suspend fun getRecipeById(id:String): Response<MealResponse>{
        return apiService.getRecipeById(id)
    }

    suspend fun getRandomRecipe(): Response<MealResponse>{
        return apiService.getRandomRecipe()
    }
}