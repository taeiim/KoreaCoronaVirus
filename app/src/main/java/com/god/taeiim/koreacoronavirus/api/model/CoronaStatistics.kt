package com.god.taeiim.koreacoronavirus.api.model

data class CoronaStatistics(
    val coronaCountryCnt: Int = 0,
    val koreaCancelIsolationCnt: Int = 0,
    val koreaConfirmatorCnt: Int = 0,
    val koreaDieCnt: Int = 0,
    val koreaCuredCnt: Int = 0,
    val koreaIsolationCnt: Int = 0,
    val koreaSymptomCnt: Int = 0,
    val updateTime: String = "",
    val worldConfirmatorCnt: Int = 0,
    val worldDieCnt: Int = 0,
    val worldCuredCnt: Int = 0,
    val titleMsg: String = "마스크 착용 잊지마세요!"
)
