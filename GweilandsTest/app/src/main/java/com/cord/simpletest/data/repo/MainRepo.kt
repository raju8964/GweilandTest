package com.cord.simpletest.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cord.simpletest.data.room.MyDao
import com.cord.simpletest.ui.components.chatscreen.model.Task
import com.cord.simpletest.utils.network.Result


class MainRepo(
    private val dao: MyDao,
) {

    private val messageLiveData = MutableLiveData<Result<String>>()
    val responseLive: LiveData<Result<String>>
        get() = messageLiveData

    suspend fun saveMessage(message: String) {
        var task = Task(
            id = 0,
            message = message)
        messageLiveData.postValue(Result.Loading())
        try {
            dao.insert(task)
            messageLiveData.postValue(Result.Success("success"))
        } catch (e: Exception) {
            messageLiveData.postValue(Result.Error(e))
        }

    }
    suspend fun getData():MyDao{
        return dao
    }

}

