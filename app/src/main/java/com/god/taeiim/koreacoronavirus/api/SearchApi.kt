package com.god.taeiim.koreacoronavirus.api

import com.god.taeiim.koreacoronavirus.api.model.SearchResultNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/news.json")
    fun searchContents(@Query("query") query: String): Call<SearchResultNews>

}