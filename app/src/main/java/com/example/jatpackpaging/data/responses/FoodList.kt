package com.example.jatpackpaging.data.responses

import com.google.gson.annotations.SerializedName

data class FoodList(
    @SerializedName("count") var count: Int,
    @SerializedName("next") var next: String,
    @SerializedName("previous") var previous: String,
    @SerializedName("results") var results: List<Data>

)