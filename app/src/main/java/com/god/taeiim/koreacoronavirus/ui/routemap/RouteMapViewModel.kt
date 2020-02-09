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
    private val _confirmationsAddAllOption = MutableLiveData<Confirmations>()
    private val _errorFailGetConfirmationsData = MutableLiveData<Throwable>()
    private val _currentSelectIndex = MutableLiveData<Int>()
    val confirmations: LiveData<Confirmations> get() = _confirmations
    val confirmationsAddAllOption: LiveData<Confirmations> get() = _confirmationsAddAllOption
    val errorFailGetConfirmationsData: LiveData<Throwable> get() = _errorFailGetConfirmationsData
    val currentSelectIndex: LiveData<Int> get() = _currentSelectIndex



    fun getConfirmationsData() {
        firebaseRepository.getConfirmationsInfo(success = {
            _confirmations.value = it
            it.confirmations?.mapIndexed { index, confirmationInfo -> confirmationInfo.id = index }

            val addAllSelectList = arrayListOf(Confirmations.ConfirmationInfo(-1))
            addAllSelectList.addAll(it.confirmations as List)
            _confirmationsAddAllOption.value = Confirmations(addAllSelectList)

        }, fail = {
            _errorFailGetConfirmationsData.value = it
        })
    }

    fun selectSearchConfirmation(confirmation: Confirmations.ConfirmationInfo) {
        _confirmationsAddAllOption.value?.confirmations?.map { it.isSelected.set(false) }
        confirmation.isSelected.set(true)
        _currentSelectIndex.value = confirmation.id
    }
}