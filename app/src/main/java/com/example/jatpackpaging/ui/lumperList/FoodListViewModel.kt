package com.example.jatpackpaging.ui.lumperList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jatpackpaging.data.network.ApiInterFace
import com.example.jatpackpaging.data.reopsitories.FoodListRepository
import com.example.jatpackpaging.data.responses.Data

class FoodListViewModel(private val repo: FoodListRepository, private val api: ApiInterFace) :
    ViewModel() {
    private var foodList = MutableLiveData<PagingData<Data>>()


    fun getMovieList(): LiveData<PagingData<Data>>? {
        var response: LiveData<PagingData<Data>>? = null
        response = repo.fetchAllFoodData().cachedIn(viewModelScope)
        foodList = response as MutableLiveData<PagingData<Data>>
        return response
    }
}