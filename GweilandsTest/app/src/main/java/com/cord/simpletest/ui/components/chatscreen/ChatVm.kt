package com.cord.simpletest.ui.components.chatscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cord.simpletest.data.SharedPref
import com.cord.simpletest.ui.components.register.model.SignupData
import com.cord.simpletest.utils.Common
import com.cord.simpletest.utils.network.Result

class ChatVm {
    val _chatResult = MutableLiveData<Result<String>>()
    val chatResult: LiveData<Result<String>> = _chatResult


    fun sumbitData(model: SignupData) {
        _chatResult.value = Result.Loading()
        try {
            SharedPref.apply {
                saveData(Common.USERNAME, model.name)
                saveData(Common.EMAIL, model.email)
                saveData(Common.PHONE, model.phone)
                saveData(Common.IMAGE, model.image)
                if (getData(Common.USERNAME).isEmpty()) {
                    _chatResult.value = Result.LocalError("Oops!! Something went wrong")
                } else {
                    _chatResult.value = Result.Success("Successfully Registered")
                }
            }
        } catch (e: java.lang.Exception) {
            _chatResult.value = Result.Error(e)

        }

    }
}

