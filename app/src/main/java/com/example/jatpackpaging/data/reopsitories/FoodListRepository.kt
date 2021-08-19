package com.example.jatpackpaging.data.reopsitories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.jatpackpaging.data.DataPageSource
import com.example.jatpackpaging.data.network.ApiInterFace
import com.example.jatpackpaging.data.responses.Data

class FoodListRepository(private val api: ApiInterFace) {
    fun fetchAllFoodData(): LiveData<PagingData<Data>> {
        return Pager(
            config = PagingConfig(pageSize = 30, maxSize = 3000),
            pagingSourceFactory = { DataPageSource(api) }).liveData

    }
}