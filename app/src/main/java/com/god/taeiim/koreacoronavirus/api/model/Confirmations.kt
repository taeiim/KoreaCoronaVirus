package com.god.taeiim.koreacoronavirus.api.model

data class Confirmations(
    val confirmations: List<ConirmationInfo>
) {
    data class ConirmationInfo(
        val age: Int? = 0,
        val confirmDate: String? = "",
        val contactPersonCnt: Int? = 0,
        val cureHospital: String? = "",
        val gender: String? = "",
        val isShow: Boolean? = true,
        val isVisitWuhan: Boolean? = true,
        val marker: List<Marker>? = null,
        val memo: String? = "",
        val nationality: String? = ""
    )
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