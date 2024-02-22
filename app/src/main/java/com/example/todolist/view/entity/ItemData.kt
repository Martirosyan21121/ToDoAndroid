package com.example.todolist.view.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
@Entity(tableName = "item_data")
data class ItemData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val number: Int,
    val time: LocalTime? = null,
    var completed: Boolean = false
): Parcelable

