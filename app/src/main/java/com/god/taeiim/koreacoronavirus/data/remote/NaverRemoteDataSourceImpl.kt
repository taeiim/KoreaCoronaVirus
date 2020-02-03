package com.god.taeiim.koreacoronavirus.data.remote

import com.god.taeiim.koreacoronavirus.api.model.SearchResultNews
import com.god.taeiim.koreacoronavirus.api.provideAuthApi
import com.god.taeiim.koreacoronavirus.data.NaverDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NaverRemoteDataSourceImpl : NaverDataSource.RemoteDataSource {

    override fun getSearchNews(
        query: String,
        success: (results: SearchResultNews) -> Unit,
        fail: (t: Throwable) -> Unit
    ) {
        provideAuthApi().searchContents(query)
            .enqueue(object : Callback<SearchResultNews> {
                override fun onResponse(
                    call: Call<SearchResultNews>,
                    response: Response<SearchResultNews>
                ) {
                    val result = response.body()
                    if (result != null) success(result)
                    else fail(IllegalStateException())
                }

                override fun onFailure(call: Call<SearchResultNews>, t: Throwable) {
                    fail(t)
                }
            })
    }

}
