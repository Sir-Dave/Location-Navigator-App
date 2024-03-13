package com.sirdave.campusnavigator.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDateTime.toFormattedDate(): String{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return this.format(formatter)
}

fun String.toLocalDateTime(): LocalDateTime{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return LocalDateTime.parse(this, formatter)
}