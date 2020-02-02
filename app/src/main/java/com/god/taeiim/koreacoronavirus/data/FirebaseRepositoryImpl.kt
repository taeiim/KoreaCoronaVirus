package com.god.taeiim.koreacoronavirus.data

import com.god.taeiim.koreacoronavirus.api.model.CoronaStatistics

class FirebaseRepositoryImpl private constructor(
    private val firebaseRemote: FirebaseDataSource.RemoteDataSource
) : FirebaseRepository {

    override fun getStatisticsData(
        success: (results: CoronaStatistics) -> Unit,
        fail: (t: Throwable) -> Unit
    ) {
        firebaseRemote.getStatisticsData(success, fail)
    }

    override fun getWebViewURL(
        success: (results: String) -> Unit,
        fail: (t: Throwable) -> Unit
    ) {
        firebaseRemote.getWebViewURL(success, fail)
    }

    companion object {
        private var INSTANCE: FirebaseRepositoryImpl? = null

        fun getInstance(
            remoteDataSource: FirebaseDataSource.RemoteDataSource
        ): FirebaseRepositoryImpl {
            return INSTANCE ?: FirebaseRepositoryImpl(remoteDataSource)
                .apply { INSTANCE = this }
        }
    }

}