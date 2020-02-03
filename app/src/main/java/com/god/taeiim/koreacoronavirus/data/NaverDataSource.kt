package com.god.taeiim.koreacoronavirus.data

import com.god.taeiim.koreacoronavirus.api.model.SearchResultNews

interface NaverDataSource {

    interface RemoteDataSource {

        fun getSearchNews(
            query: String,
            success: (results: SearchResultNews) -> Unit,
            fail: (t: Throwable) -> Unit
        )

    }

}