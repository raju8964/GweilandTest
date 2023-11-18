package com.cord.simpletest.ui.components.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cord.simpletest.data.SharedPref
import com.cord.simpletest.ui.components.register.model.SignupData
import com.cord.simpletest.utils.Common
import com.cord.simpletest.utils.network.Result

class SignupVm : ViewModel() {
    val _signupResult = MutableLiveData<Result<String>>()
    val signupResult: LiveData<Result<String>> = _signupResult
    var isValid=MutableLiveData<Boolean>()

   fun sumbitData(model: SignupData) {
        _signupResult.postValue(Result.Loading())
        try {
            SharedPref.apply {
                saveData(Common.USERNAME, model.name)
                saveData(Common.EMAIL, model.email)
                saveData(Common.PHONE, model.phone)
                saveData(Common.IMAGE, model.image)
                if (getData(Common.USERNAME).isEmpty()) {
                    _signupResult.postValue( Result.LocalError("Oops!! Something went wrong"))
                } else {
                    _signupResult.postValue(Result.Success("Successfully Registered"))
                }
            }
        } catch (e: java.lang.Exception) {
            _signupResult.postValue(Result.Error(e))
        }
    }

}