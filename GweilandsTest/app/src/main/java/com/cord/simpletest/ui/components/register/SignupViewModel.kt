//package com.app.vstock.ui.signup.viewModel
//
//import androidx.annotation.VisibleForTesting
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import com.app.vstock.data.Resource
//import com.app.vstock.data.local.SharedPref
//import com.app.vstock.data.remote.Event
//import com.app.vstock.data.repository.UserRepository
//import com.app.vstock.ui.signup.model.Data
//import com.app.vstock.ui.signup.model.SignUpResponse
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import okhttp3.RequestBody
//import okhttp3.RequestBody.Companion.toRequestBody
//import javax.inject.Inject
//
///*
//    MainViewModel is a class that is responsible for
//    preparing and managing the data for  MainActivity
// */
//@HiltViewModel
//class SignupViewModel @Inject constructor(
//    private val userRepository: UserRepository, private val sharedPref: SharedPref
//) : BaseViewModel() {
//
//    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
//    val signupLiveDataPrivate = MutableLiveData<Event<Resource<SignUpResponse>>>()
//    val signupLiveData: LiveData<Event<Resource<SignUpResponse>>> get() = signupLiveDataPrivate
//
//    /*
//       Call Sign Up User API
//     */
//    fun signup() {
//        signUpRequestData.value?.let {
//            it.role_id = "2"
//        }
//        viewModelScope.launch {
//            val response = signUpRequestData.value?.let {
//                userRepository.doSignUp(
//                    signUpRequestData.value!!
//                )
//            }
//            withContext(Dispatchers.Main) {
//                response?.collect { signupLiveDataPrivate.postValue(Event(it)) }
//            }
//        }
//    }
//
//    fun saveUser(data: Data?) {
//        sharedPref.saveUser(data)
//    }
//    fun saveToken(token: String?) {
//        sharedPref.saveToken(token)
//    }
//
//
//}