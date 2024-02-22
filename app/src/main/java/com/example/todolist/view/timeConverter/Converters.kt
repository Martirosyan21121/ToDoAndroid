package com.example.todolist.view.timeConverter

import androidx.room.TypeConverter
import java.time.LocalTime

class Converters {

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it) }
    }
}