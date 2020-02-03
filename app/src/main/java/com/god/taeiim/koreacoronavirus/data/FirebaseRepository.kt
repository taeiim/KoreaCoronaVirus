package com.god.taeiim.koreacoronavirus.data

import com.god.taeiim.koreacoronavirus.api.model.CoronaStatistics

interface FirebaseRepository {

    fun getStatisticsData(
        success: (results: CoronaStatistics) -> Unit,
        fail: (t: Throwable) -> Unit
    )

    fun getHotSearchKeyword(
        success: (keywords: ArrayList<String>) -> Unit,
        fail: (t: Throwable) -> Unit
    )

    fun getWebViewURL(
        success: (results: String) -> Unit,
        fail: (t: Throwable) -> Unit
    )

}