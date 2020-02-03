package com.god.taeiim.koreacoronavirus.api.model

data class SearchResultNews(
    val items: List<Item>
) {
    data class Item(
        val title: String?,
        val pubDate: String?,
        val description: String?,
        val link: String?,
        val originallink: String?
    )
}

