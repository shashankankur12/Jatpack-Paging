package com.example.jatpackpaging.ui.lumperList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jatpackpaging.data.network.ApiInterFace
import com.example.jatpackpaging.data.reopsitories.FoodListRepository

@Suppress("UNCHECKED_CAST")
class FoodListViewModelFactory(
    private val repository: FoodListRepository,
    private val api: ApiInterFace
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FoodListViewModel(repository, api) as T
    }
}