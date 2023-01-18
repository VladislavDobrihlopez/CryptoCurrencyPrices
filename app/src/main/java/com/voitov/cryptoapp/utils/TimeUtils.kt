package com.voitov.cryptoapp.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToTime(timestamp: Long?): String {
    if (timestamp == null) {
        return ""
    }

    val stamp = Timestamp(timestamp * 1000)
    val pattern = "HH:mm:ss"
    val date = Date(stamp.time)
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()
    return simpleDateFormat.format(date)
}