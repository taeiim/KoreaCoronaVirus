package com.god.taeiim.koreacoronavirus.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.god.taeiim.koreacoronavirus.api.model.CoronaStatistics
import com.god.taeiim.koreacoronavirus.data.FirebaseRepository

class CoronaStatisticsViewModel(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _coronaStatistics = MutableLiveData<CoronaStatistics>()
    private val _errorFailGetData = MutableLiveData<Throwable>()
    val errorFailGetData: LiveData<Throwable> get() = _errorFailGetData
    val coronaStatistics: LiveData<CoronaStatistics> get() = _coronaStatistics

    val coronaCountryCnt = MutableLiveData<Int>()
    val koreaCancelIsolationCnt = MutableLiveData<Int>()
    val koreaConfirmatorCnt = MutableLiveData<Int>()
    val koreaDieCnt = MutableLiveData<Int>()
    val koreaCuredCnt = MutableLiveData<Int>()
    val koreaIsolationCnt = MutableLiveData<Int>()
    val koreaSymptomCnt = MutableLiveData<Int>()
    val updateTime = MutableLiveData<String>()
    val worldConfirmatorCnt = MutableLiveData<Int>()
    val worldDieCnt = MutableLiveData<Int>()
    val worldCuredCnt = MutableLiveData<Int>()
    val titleMsg = MutableLiveData<String>()

    fun getStatisticsData() {
        firebaseRepository.getStatisticsData(success = {
            _coronaStatistics.value = it
            setStatisticsLiveData()
        }, fail = {
            _errorFailGetData.value = it
        })
    }

    private fun setStatisticsLiveData() {
        coronaStatistics.value?.let {
            coronaCountryCnt.value = it.coronaCountryCnt
            koreaCancelIsolationCnt.value = it.koreaCancelIsolationCnt
            koreaConfirmatorCnt.value = it.koreaConfirmatorCnt
            koreaDieCnt.value = it.koreaDieCnt
            koreaCuredCnt.value = it.koreaCuredCnt
            koreaIsolationCnt.value = it.koreaIsolationCnt
            koreaSymptomCnt.value = it.koreaSymptomCnt
            updateTime.value = it.updateTime
            worldConfirmatorCnt.value = it.worldConfirmatorCnt
            worldDieCnt.value = it.worldDieCnt
            worldCuredCnt.value = it.worldCuredCnt
            titleMsg.value = it.titleMsg
        }
    }

}