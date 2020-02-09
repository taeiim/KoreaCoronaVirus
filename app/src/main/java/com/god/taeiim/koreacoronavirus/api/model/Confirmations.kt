package com.god.taeiim.koreacoronavirus.api.model

import androidx.databinding.ObservableField

data class Confirmations(
    val confirmations: List<ConfirmationInfo>
) {
    data class ConfirmationInfo(
        var id: Int? = 0,
        val age: Int? = 0,
        val confirmDate: String? = "",
        val contactPersonCnt: Int? = 0,
        val cureHospital: String? = "",
        val gender: String? = "",
        val isShow: Boolean? = true,
        val isVisitWuhan: Boolean? = true,
        val marker: List<Marker>? = null,
        val memo: String? = "",
        val nationality: String? = "",
        var isSelected: ObservableField<Boolean> = ObservableField(false)
    ) {
        val showId: String
            get() = id?.let { if (it == -1) "# 전체" else "# ${it + 1}번째" } ?: "1번째"
    }
}

data class Marker(
    val date: String? = "",
    val latitude: String? = null,
    val longitude: String? = null,
    val name: String? = ""
)

data class MarkersInMap(
    val index: Int,
    val marker: com.google.android.gms.maps.model.Marker
)