package com.god.taeiim.koreacoronavirus.ui.routemap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.god.taeiim.koreacoronavirus.api.model.Confirmations
import com.god.taeiim.koreacoronavirus.data.FirebaseRepository

class RouteMapViewModel(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _confirmations = MutableLiveData<Confirmations>()
    private val _errorFailGetConfirmationsData = MutableLiveData<Throwable>()
    val confirmations: LiveData<Confirmations> get() = _confirmations
    val errorFailGetConfirmationsData: LiveData<Throwable> get() = _errorFailGetConfirmationsData


    fun getConfirmationsData() {
        firebaseRepository.getConfirmationsInfo(success = {
            _confirmations.value = it

        }, fail = {
            _errorFailGetConfirmationsData.value = it
        })
    }

}