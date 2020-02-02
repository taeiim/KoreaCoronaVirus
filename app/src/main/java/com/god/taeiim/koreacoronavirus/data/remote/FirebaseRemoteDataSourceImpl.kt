package com.god.taeiim.koreacoronavirus.data.remote

import com.god.taeiim.koreacoronavirus.api.model.CoronaStatistics
import com.god.taeiim.koreacoronavirus.data.FirebaseDataSource
import com.google.firebase.database.*

class FirebaseRemoteDataSourceImpl : FirebaseDataSource.RemoteDataSource {

    override fun getStatisticsData(
        success: (results: CoronaStatistics) -> Unit,
        fail: (t: Throwable) -> Unit
    ) {

        val database = FirebaseDatabase.getInstance().reference
        val myRef: DatabaseReference = database.root.child("corona-statistics").child("statistics")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val coronaStatistics: CoronaStatistics? =
                    dataSnapshot.getValue(CoronaStatistics::class.java)
                coronaStatistics?.let(success) ?: fail(Throwable("null"))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                fail(Throwable())
            }
        }
        myRef.addListenerForSingleValueEvent(postListener)

    }

    companion object {
        private var instance: FirebaseDataSource.RemoteDataSource? = null

        fun getInstance() = instance ?: FirebaseRemoteDataSourceImpl()
            .apply { instance = this }
    }

}