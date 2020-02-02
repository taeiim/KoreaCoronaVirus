package com.god.taeiim.koreacoronavirus.data

import com.god.taeiim.koreacoronavirus.api.model.CoronaStatistics

interface FirebaseDataSource {

    interface RemoteDataSource {

        fun getStatisticsData(
            success: (results: CoronaStatistics) -> Unit,
            fail: (t: Throwable) -> Unit
        )

    }

}