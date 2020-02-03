package com.god.taeiim.koreacoronavirus.data

import com.god.taeiim.koreacoronavirus.api.model.SearchResultNews

class NaverRepositoryImpl private constructor(
    private val naverRemote: NaverDataSource.RemoteDataSource
) : NaverRepository {

    override fun getSearchNews(
        query: String,
        success: (results: SearchResultNews) -> Unit,
        fail: (t: Throwable) -> Unit
    ) {
        naverRemote.getSearchNews(query, success, fail)
    }

    companion object {

        private var INSTANCE: NaverRepositoryImpl? = null

        fun getInstance(
            tasksRemoteDataSource: NaverDataSource.RemoteDataSource
        ): NaverRepositoryImpl {
            return INSTANCE ?: NaverRepositoryImpl(tasksRemoteDataSource)
                .apply { INSTANCE = this }
        }

    }

}