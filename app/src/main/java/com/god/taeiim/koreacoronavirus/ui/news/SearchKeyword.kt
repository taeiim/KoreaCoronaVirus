package com.god.taeiim.koreacoronavirus.ui.news

import androidx.databinding.ObservableField

data class SearchKeyword(
    val id: Int,
    val keyword: String,
    val isSelected: ObservableField<Boolean> = ObservableField(false)
) {
    val hashTagKeyword: String
        get() = "# $keyword"
}

