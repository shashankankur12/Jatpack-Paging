package com.example.jatpackpaging.data

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jatpackpaging.data.network.ApiInterFace
import com.example.jatpackpaging.data.responses.Data
import retrofit2.HttpException
import java.io.IOException

class DataPageSource(private val api: ApiInterFace) : PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val nextPage = params.key ?: FIRST_PAGE_INDEX
            println("$nextPage=========================")
            val response = api.getAllFoodData(
                "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
                nextPage.toString(),
                ""
            )
            var nextPageNumber: Int? = null
            if (response.body()?.next != null) {
                val uri = Uri.parse(response.body()?.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            var prevPageNumber: Int? = null
            if (response.body()?.previous != null) {
                val uri = Uri.parse(response.body()?.previous!!)
                val prevPageQuery = uri.getQueryParameter("page")

                prevPageNumber = prevPageQuery?.toInt()
            }

            LoadResult.Page(
                data = response.body()?.results!!,
                prevKey = /*if (nextPage == FIRST_PAGE_INDEX) null else nextPage - 1*/ prevPageNumber,
                nextKey = /*if (nextPageNumber == null) null else nextPage + 1*/ nextPageNumber
            )
        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}