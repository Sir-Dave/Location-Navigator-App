package com.sirdave.campusnavigator.util

import android.icu.text.DecimalFormat
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.DirectionWithIcon
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

val mapOfTurns = mapOf(
    "right" to R.drawable.baseline_turn_right,
    "left" to R.drawable.baseline_turn_left,
    "up" to R.drawable.baseline_keyboard_double_arrow_up,
    "down" to R.drawable.baseline_keyboard_double_arrow_down,
    "roundabout" to R.drawable.round_about
)

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

fun formatTime(duration: Double): String{
    var remainingSeconds = duration.toInt()
    val hours = remainingSeconds / 3600
    remainingSeconds %= 3600
    val minutes = remainingSeconds / 60
    val secs = remainingSeconds % 60

    val timeComponents = mutableListOf<String>()

    if (hours > 0) {
        timeComponents.add("$hours hour${if (hours > 1) "s" else ""}")
    }
    if (minutes > 0) {
        timeComponents.add("$minutes minute${if (minutes > 1) "s" else ""}")
    }
    if (secs > 0) {
        timeComponents.add("$secs second${if (secs > 1) "s" else ""}")
    }

    return timeComponents.joinToString(separator = " ")
}

fun formatDistance(length: Double): String{
    val df = DecimalFormat("#.##")
    if (length >= 1) return "${df.format(length)}km"
    val metre = (length * 1000).toInt()
    return "${metre}m"
}

fun assignIconToDirection(directions: String): DirectionWithIcon{
    val words = directions.split(" ")
    for (word in words){
        if (mapOfTurns.contains(word)){
            return DirectionWithIcon(
                text = directions,
                directionIcon = mapOfTurns[word]!!
            )
        }
    }
    return DirectionWithIcon(
        text = directions,
        R.drawable.baseline_keyboard_double_arrow_up
    )
}