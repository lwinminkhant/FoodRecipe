package com.lmkhant.foodrecipedb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lmkhant.foodrecipedb.common.Constants
import com.lmkhant.foodrecipedb.common.Resource
import com.lmkhant.foodrecipedb.common.network.NetworkConnectivityInterceptor
import com.lmkhant.foodrecipedb.model.FilterResponse
import com.lmkhant.foodrecipedb.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val internetConnection: NetworkConnectivityInterceptor,
) : BaseViewModel() {

    val filters = MutableLiveData<Resource<FilterResponse>>()

    fun getFilterByArea(filterQuery: String) = viewModelScope.launch{
        postResponse(repository.getFilterByArea(filterQuery))
    }
    fun getFilterByIngredient(filterQuery: String) = viewModelScope.launch {
        postResponse(repository.getFilterByIngredient(filterQuery))
    }
    fun getFilterByCategory(filterQuery: String) = viewModelScope.launch{
        postResponse(repository.getFilterByCategory(filterQuery))
    }
    fun getFilterBySearch(filterQuery: String) = viewModelScope.launch{
        postResponse(repository.search(filterQuery))
    }

    private fun postResponse(response: Response<FilterResponse>){
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