package com.example.jatpackpaging.data.responses

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("pk") var pk: Int,
    @SerializedName("title") var title: String,
    @SerializedName("publisher") var publisher: String,
    @SerializedName("featured_image") var featuredImage: String,
    @SerializedName("rating") var rating: Int,
    @SerializedName("source_url") var sourceUrl: String,
    @SerializedName("description") var description: String,
    @SerializedName("cooking_instructions") var cookingInstructions: String,
    @SerializedName("ingredients") var ingredients: List<String>,
    @SerializedName("date_added") var dateAdded: String,
    @SerializedName("date_updated") var dateUpdated: String,
    @SerializedName("long_date_added") var longDateAdded: Int,
    @SerializedName("long_date_updated") var longDateUpdated: Int

)