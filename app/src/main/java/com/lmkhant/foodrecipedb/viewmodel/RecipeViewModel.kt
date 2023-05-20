package com.lmkhant.foodrecipedb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lmkhant.foodrecipedb.common.Resource
import com.lmkhant.foodrecipedb.common.network.NetworkConnectivityInterceptor
import com.lmkhant.foodrecipedb.model.Meal
import com.lmkhant.foodrecipedb.model.MealResponse
import com.lmkhant.foodrecipedb.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val internetConnection: NetworkConnectivityInterceptor,
) : BaseViewModel() {

    val meals = MutableLiveData<Resource<MealResponse>>()

    fun getMealById(id: String) = viewModelScope.launch {
        postResponse(repository.getRecipeById(id))
    }

    fun getRandomMeal() = viewModelScope.launch {
        postResponse(repository.getRandomRecipe())
    }

    private fun postResponse(response: Response<MealResponse>) {
        meals.postValue(Resource.Loading())
        try {
            if (internetConnection.isConnected()) {
                meals.postValue(handleResponse(response = response))

                /*if(internetConnection.isServerAvailable()=="OK"){
                }else{
                    meals.postValue(Resource.Error(message = internetConnection.isServerAvailable()))
                }*/
            }else {
                meals.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> meals.postValue(Resource.Error(message = "Network failure"))
                else -> meals.postValue(Resource.Error(message = "Conversion error"))
            }
        }

    }
    fun getYouTubeId(youTubeUrl: String): String? {
        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern: Pattern = Pattern.compile(pattern)
        val matcher: Matcher = compiledPattern.matcher(youTubeUrl)
        return if (matcher.find()) {
            matcher.group()
        }else return null
    }
    fun getIngredients(meal: Meal): String {

        var ingredients = ""

        if (meal.strIngredient1?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure1 + " " + meal.strIngredient1 + "\n"
        }
        if (meal.strIngredient2?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure2 + " " + meal.strIngredient2 + "\n"
        }
        if (meal.strIngredient3?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure3 + " " + meal.strIngredient3 + "\n"
        }
        if (meal.strIngredient4?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure4 + " " + meal.strIngredient4 + "\n"
        }
        if (meal.strIngredient5?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure5 + " " + meal.strIngredient5 + "\n"
        }
        if (meal.strIngredient6?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure6 + " " + meal.strIngredient6 + "\n"
        }
        if (meal.strIngredient7?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure7 + " " + meal.strIngredient7 + "\n"
        }
        if (meal.strIngredient8?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure8 + " " + meal.strIngredient8 + "\n"
        }
        if (meal.strIngredient9?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure9 + " " + meal.strIngredient9 + "\n"
        }
        if (meal.strIngredient10?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure10 + " " + meal.strIngredient10 + "\n"
        }
        if (meal.strIngredient11?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure11 + " " + meal.strIngredient11 + "\n"
        }
        if (meal.strIngredient12?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure12 + " " + meal.strIngredient12 + "\n"
        }
        if (meal.strIngredient13?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure13 + " " + meal.strIngredient13 + "\n"
        }
        if (meal.strIngredient14?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure14 + " " + meal.strIngredient14 + "\n"
        }
        if (meal.strIngredient15?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure15 + " " + meal.strIngredient15 + "\n"
        }
        if (meal.strIngredient16?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure16 + " " + meal.strIngredient16 + "\n"
        }
        if (meal.strIngredient17?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure17 + " " + meal.strIngredient17 + "\n"
        }
        if (meal.strIngredient18?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure18 + " " + meal.strIngredient18 + "\n"
        }
        if (meal.strIngredient19?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure19 + " " + meal.strIngredient19 + "\n"
        }
        if (meal.strIngredient20?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure20 + " " + meal.strIngredient20 + "\n"
        }

        return ingredients

    }

}