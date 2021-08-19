package com.example.jatpackpaging

import android.app.Application
import com.example.jatpackpaging.data.DataPageSource
import com.example.jatpackpaging.data.network.ApiInterFace
import com.example.jatpackpaging.data.reopsitories.FoodListRepository
import com.example.jatpackpaging.ui.lumperList.FoodListViewModelFactory
import com.example.jatpackpaging.utils.NetworkConnectionInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class MyApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiInterFace(instance()) }
        bind() from singleton { DataPageSource(instance()) }
        bind() from singleton { FoodListRepository(instance()) }
        bind() from provider { FoodListViewModelFactory(instance(), instance()) }
    }


}