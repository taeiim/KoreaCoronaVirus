package com.god.taeiim.koreacoronavirus.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.god.taeiim.koreacoronavirus.api.model.SearchResultNews
import com.god.taeiim.koreacoronavirus.data.FirebaseRepository
import com.god.taeiim.koreacoronavirus.data.NaverRepository

class NewsViewModel(
    private val firebaseRepository: FirebaseRepository,
    private val naverRepository: NaverRepository
) : ViewModel() {

    private val _errorFailGetKeywords = MutableLiveData<Throwable>()
    private val _hotSearchKeywords = MutableLiveData<List<SearchKeyword>>()
    private val _currentSearchKeyword = MutableLiveData<String>()
    private val _searchNewsResultList = MutableLiveData<List<SearchResultNews.Item>>()
    private val _errorFailSearchNews = MutableLiveData<Throwable>()
    val errorFailGetKeywords: LiveData<Throwable> get() = _errorFailGetKeywords
    val hotSearchKeywords: LiveData<List<SearchKeyword>> get() = _hotSearchKeywords
    val currentSearchKeyword: LiveData<String> get() = _currentSearchKeyword
    val searchNewsResultList: LiveData<List<SearchResultNews.Item>> get() = _searchNewsResultList
    val errorFailSearchNews get() = _errorFailSearchNews

    fun getHotSearchKeyword() {
        firebaseRepository.getHotSearchKeyword(success = {
            _hotSearchKeywords.value =
                it.mapIndexed { index, keyword -> SearchKeyword(index, keyword) }
            hotSearchKeywords.value?.let { words -> selectSearchKeyword(words[0]) }

        }, fail = {
            _errorFailGetKeywords.value = it
        })
    }

    fun selectSearchKeyword(keyword: SearchKeyword) {
        hotSearchKeywords.value?.map { it.isSelected.set(false) }
        keyword.isSelected.set(true)
        _currentSearchKeyword.value = keyword.keyword
    }

    fun searchNews() {
        naverRepository.getSearchNews(
            currentSearchKeyword.value ?: "코로나 바이러스",
            success = {
                _searchNewsResultList.value = it.items
            },
            fail = {
                _errorFailSearchNews.value = it
            }
        )
    }

}