package com.example.template.data.csv

import com.example.template.data.mapper.toIntraday
import com.example.template.data.remote.dto.IntradayDto
import com.example.template.domain.model.Intraday
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayCSVParser @Inject constructor() : CSVParser<Intraday> {
    override suspend fun parse(stream: InputStream): List<Intraday> {
        val intradayReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            intradayReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(1) ?: return@mapNotNull null
                    val dto = IntradayDto(timestamp, close.toDouble())
                    dto.toIntraday()
                }
                .filter {
                    it.date.dayOfMonth == LocalDate.now().minusDays(4).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    intradayReader.close()
                }
        }
    }
}