package com.example.template.data.mapper

import com.example.template.data.remote.dto.IntradayDto
import com.example.template.domain.model.Intraday
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntradayDto.toIntraday(): Intraday{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return Intraday(
        date = localDateTime,
        close = close
    )
}
//fun Intraday.toIntraday(): IntradayDto{}