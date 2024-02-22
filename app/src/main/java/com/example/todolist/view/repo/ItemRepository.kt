package com.example.todolist.view.repo

import androidx.lifecycle.LiveData
import com.example.todolist.view.dao.ItemDao
import com.example.todolist.view.entity.ItemData

class ItemRepository(private val itemDao: ItemDao) {

    val getAllData: LiveData<List<ItemData>> = itemDao.getAllTasks()

    suspend fun addItem(itemData: ItemData){
        itemDao.insert(itemData)
    }

    suspend fun itemDataUpdate(itemData: ItemData) {
        itemDao.update(itemData)
    }

    suspend fun deleteItemData(itemData: ItemData) {
        itemDao.delete(itemData)
    }
}