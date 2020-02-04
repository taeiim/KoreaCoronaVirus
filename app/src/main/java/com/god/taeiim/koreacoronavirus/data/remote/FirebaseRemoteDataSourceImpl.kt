package com.god.taeiim.koreacoronavirus.data.remote

import com.god.taeiim.koreacoronavirus.api.model.Confirmations
import com.god.taeiim.koreacoronavirus.api.model.CoronaStatistics
import com.god.taeiim.koreacoronavirus.data.FirebaseDataSource
import com.google.firebase.database.*

object FirebaseRemoteDataSourceImpl : FirebaseDataSource.RemoteDataSource {

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

    override fun getConfirmationsInfo(
        success: (results: Confirmations) -> Unit,
        fail: (t: Throwable) -> Unit
    ) {

        val confirmations = ArrayList<Confirmations.ConirmationInfo>()
        val myRef: DatabaseReference = database.root.child("confirmations-info")

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (item in dataSnapshot.children) {
                    confirmations.add(
                        item.key?.toInt() ?: 0,
                        item.getValue(Confirmations.ConirmationInfo::class.java)!!
                    )
                }
                success(Confirmations(confirmations))

            }

            override fun onCancelled(databaseError: DatabaseError) {
                fail(Throwable())
            }
        })

    }

    override fun getHotSearchKeyword(
        success: (keywords: ArrayList<String>) -> Unit,
        fail: (t: Throwable) -> Unit
    ) {
        val myRef: DatabaseReference = database.root.child("hot-keyword")

        val event = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val keywords = dataSnapshot.value as ArrayList<String>
                success(keywords)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                fail(Throwable())
            }
        }

        myRef.addListenerForSingleValueEvent(event)

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


}