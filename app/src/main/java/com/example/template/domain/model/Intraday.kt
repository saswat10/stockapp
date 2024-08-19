package com.example.template.domain.model

import java.time.LocalDateTime

data class Intraday(
    val date: LocalDateTime,
    val close: Double
)
