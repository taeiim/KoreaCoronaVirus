package com.god.taeiim.koreacoronavirus.api.model

data class Confirmations(
    val confirmations: List<ConirmationInfo>
) {
    data class ConirmationInfo(
        val age: Int?,
        val confirmDate: String?,
        val contactPersonCnt: Int?,
        val cureHospital: String?,
        val gender: String?,
        val isShow: Boolean?,
        val isVisitWuhan: Boolean?,
        val marker: List<Marker>,
        val memo: String?,
        val nationality: String?
    )
}

data class Marker(
    val date: String?,
    val latitude: Long?,
    val longitude: String?,
    val name: String?
)
