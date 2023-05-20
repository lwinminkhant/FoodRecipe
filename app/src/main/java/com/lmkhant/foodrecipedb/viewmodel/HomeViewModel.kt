package com.lmkhant.foodrecipedb.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lmkhant.foodrecipedb.common.Resource
import com.lmkhant.foodrecipedb.common.network.NetworkConnectivityInterceptor
import com.lmkhant.foodrecipedb.model.CategoryResponse
import com.lmkhant.foodrecipedb.model.FilterResponse

import com.lmkhant.foodrecipedb.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val repository: FoodRepository,
    private val internetConnection: NetworkConnectivityInterceptor,
) : BaseViewModel() {

    val categories = MutableLiveData<Resource<CategoryResponse>>()
    val areas = MutableLiveData<Resource<FilterResponse>>()
    val ingredients = MutableLiveData<Resource<FilterResponse>>()

    fun getCategories() = viewModelScope.launch {
        categories.postValue(Resource.Loading())
        try {
            if (internetConnection.isConnected()) {
                val response = repository.getCategories()
                categories.postValue(handleResponse(response = response))

            } else {
                categories.postValue(Resource.Error(message = "No internet connection"))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> categories.postValue(Resource.Error(message = "Network failure"))
                else -> categories.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }

    fun getMealsByArea() = viewModelScope.launch {
        //postResponse(repository.getAreaList(), areas)
        areas.postValue(Resource.Loading())
        try {
            if (internetConnection.isConnected()) {
                val response = repository.getAreaList()
                areas.postValue(handleResponse(response = response))

            } else {
                areas.postValue(Resource.Error(message = "No internet connection"))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> areas.postValue(Resource.Error(message = "Network failure"))
                else -> areas.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }

    fun getMealsByIngredient() = viewModelScope.launch {
        //postResponse(repository.getIngredientList(),ingredients)

        ingredients.postValue(Resource.Loading())
        try {
            if (internetConnection.isConnected()) {
                val response = repository.getIngredientList()
                ingredients.postValue(handleResponse(response = response))

            } else {
                ingredients.postValue(Resource.Error(message = "No internet connection"))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> ingredients.postValue(Resource.Error(message = "Network failure"))
                else -> ingredients.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }

    private fun postResponse(
        response: Response<FilterResponse>,
        filters: MutableLiveData<Resource<FilterResponse>>,
    ) {
        filters.postValue(Resource.Loading())

        try {
            if (internetConnection.isConnected()) {
                filters.postValue(handleResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }

    private fun postCResponse(
        response: Response<CategoryResponse>,
        filters: MutableLiveData<Resource<CategoryResponse>>,
    ) {
        filters.postValue(Resource.Loading())
        try {
            if (internetConnection.isConnected()) {
                filters.postValue(handleResponse(response = response))

            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }
}

