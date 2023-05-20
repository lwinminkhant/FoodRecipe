package com.lmkhant.foodrecipedb.viewmodel

import androidx.lifecycle.ViewModel
import com.lmkhant.foodrecipedb.common.Resource
import retrofit2.Response

open class BaseViewModel: ViewModel() {
    fun <T> handleResponse(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }

}