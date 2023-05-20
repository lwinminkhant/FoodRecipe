package com.lmkhant.foodrecipedb.model

import java.io.Serializable

data class FilterQuery(
    val queryType:String,
    val query:String
):Serializable