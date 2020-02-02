package com.god.taeiim.koreacoronavirus.data.remote

import com.god.taeiim.koreacoronavirus.api.model.CoronaStatistics
import com.god.taeiim.koreacoronavirus.data.FirebaseDataSource
import com.google.firebase.database.*

class FirebaseRemoteDataSourceImpl : FirebaseDataSource.RemoteDataSource {

    val database: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference }

    override fun getStatisticsData(
        success: (results: CoronaStatistics) -> Unit,
        fail: (t: Throwable) -> Unit
    ) {

        val myRef: DatabaseReference = database.root.child("corona-statistics").child("statistics")

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val coronaStatistics: CoronaStatistics? =
                    dataSnapshot.getValue(CoronaStatistics::class.java)
                coronaStatistics?.let(success) ?: fail(Throwable("null"))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                fail(Throwable())
            }
        })

    }

    override fun getWebViewURL(
        success: (results: String) -> Unit,
        fail: (t: Throwable) -> Unit
    ) {

        val myRef: DatabaseReference = database.root.child("webViewURL")

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val webViewURL: String? = dataSnapshot.value as String
                webViewURL?.let(success) ?: fail(Throwable("null"))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                fail(Throwable())
            }
        })

    }


    companion object {
        private var instance: FirebaseDataSource.RemoteDataSource? = null

        fun getInstance() = instance ?: FirebaseRemoteDataSourceImpl()
            .apply { instance = this }
    }

}