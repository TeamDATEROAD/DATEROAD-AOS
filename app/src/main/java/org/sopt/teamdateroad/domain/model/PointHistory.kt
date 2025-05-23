package org.sopt.teamdateroad.domain.model

data class PointHistory(
    val gained: List<Point> = listOf(),
    val used: List<Point> = listOf()
)
