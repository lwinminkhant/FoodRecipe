package com.lmkhant.foodrecipedb.model


import com.google.gson.annotations.SerializedName

data class FilterResponse(
    @SerializedName("meals")
    val meals: List<Meal>?
)