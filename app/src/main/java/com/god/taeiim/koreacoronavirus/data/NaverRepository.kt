package com.god.taeiim.koreacoronavirus.data

import com.god.taeiim.koreacoronavirus.api.model.SearchResultNews

interface NaverRepository {

    fun getSearchNews(
        query: String,
        success: (results: SearchResultNews) -> Unit,
        fail: (t: Throwable) -> Unit
    )

}