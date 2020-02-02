package com.god.taeiim.koreacoronavirus.ui.diseasewebview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.god.taeiim.koreacoronavirus.data.FirebaseRepository

class DiseaseControlWebViewViewModel(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _webViewURL = MutableLiveData<String>()
    private val _errorFailGetURL = MutableLiveData<Boolean>()
    val webViewURL: LiveData<String> get() = _webViewURL
    val errorFailGetURL: LiveData<Boolean> get() = _errorFailGetURL

    fun getWebViewURL() {
        firebaseRepository.getWebViewURL(success = {
            _webViewURL.value = it
            _errorFailGetURL.value = false
        }, fail = {
            _errorFailGetURL.value = true
        })
    }

}