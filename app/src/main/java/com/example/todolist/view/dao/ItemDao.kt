package com.example.todolist.view.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.view.entity.ItemData

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(itemData: ItemData)

    @Update
    suspend fun update(itemData: ItemData)

    @Delete
    suspend fun delete(itemData: ItemData)

    @Query("SELECT * FROM item_data ORDER BY id ASC")
    fun getAllTasks(): LiveData<List<ItemData>>

    @Query("SELECT * FROM item_data WHERE id = :itemId")
    fun getItemById(itemId: Long): LiveData<ItemData>?
}

