package com.example.todolist.view.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.view.dao.ItemDao
import com.example.todolist.view.entity.ItemData
import com.example.todolist.view.repo.ItemRepository
import com.example.todolist.view.roomDB.ItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    val getAllData: LiveData<List<ItemData>>
    private val repository: ItemRepository

    init {
        val itemDao = ItemDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
        getAllData = repository.getAllData
    }

    fun insert(item: ItemData) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addItem(item)
        }
    }

    fun update(item: ItemData) {
        viewModelScope.launch(Dispatchers.IO){
            repository.itemDataUpdate(item)
        }
    }

    fun delete(item: ItemData) {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteItemData(item)
        }
    }
}