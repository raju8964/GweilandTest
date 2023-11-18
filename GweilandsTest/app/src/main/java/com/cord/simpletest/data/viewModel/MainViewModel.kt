package com.cord.simpletest.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cord.simpletest.data.repo.MainRepo
import com.cord.simpletest.ui.components.chatscreen.model.Task
import com.cord.simpletest.utils.encrypt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(private val repository: MainRepo) : ViewModel() {

    fun saveData(message: String) {
        viewModelScope.launch(Dispatchers.IO) { repository.saveMessage(encrypt(message)) }
    }

    suspend fun getdata(): LiveData<List<Task>> {
        return repository.getData().getAll()!!
    }

    fun insertData(quote: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            // repository.insertQuote(quote)
        }

    }
}