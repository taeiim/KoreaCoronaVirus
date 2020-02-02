package com.god.taeiim.koreacoronavirus.api.model

data class CoronaStatistics(
    val coronaCountryCnt: Int = 0,
    val koreaCancelIsolationCnt: Int = 0,
    val koreaConfirmatorCnt: Int = 0,
    val koreaDieCnt: Int = 0,
    val koreaIsolationCnt: Int = 0,
    val koreaSymptomCnt: Int = 0,
    val updateTime: String = "",
    val worldConfirmatorCnt: Int = 0,
    val worldCoronaImg: String = "http://www.cdc.go.kr/html/a2/namoimage/images/000016/%EC%8B%A0%EC%A2%85%EC%BD%94%EB%A1%9C%EB%82%98_%EC%84%B8%EA%B3%84%EB%B0%9C%EC%83%9D%ED%98%84%ED%99%A9_200202_9%EC%8B%9C%EA%B8%B0%EC%A4%80.jpg",
    val worldDieCnt: Int = 0
)