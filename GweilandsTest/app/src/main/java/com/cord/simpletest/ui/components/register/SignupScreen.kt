package com.cord.simpletest.ui.components.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.cord.simpletest.R
import com.cord.simpletest.databinding.ActivitySignupScreenBinding
import com.cord.simpletest.ui.base.BaseActivity
import com.cord.simpletest.ui.components.chatscreen.ShowMassage
import com.cord.simpletest.ui.components.register.model.SignupData
import com.cord.simpletest.utils.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import com.cord.simpletest.utils.network.Result
import com.lassi.common.utils.KeyUtils
import com.lassi.data.media.MiMedia
import com.lassi.domain.media.LassiOption
import com.lassi.domain.media.MediaType
import com.lassi.presentation.builder.Lassi
import kotlinx.coroutines.Dispatchers
import java.io.File


// the is loagin screen -------------------------------------------
// we can use email/phone/bookingid to login
// get token
// email and password validation
// get update token
///----------------------------------------------------------------

class SignupScreen : BaseActivity() {
    lateinit var binding: ActivitySignupScreenBinding
    private val vm by viewModels<SignupVm>()

    override fun initViewBinding() {
        binding = ActivitySignupScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.apply {
            profileIcon.setOnClickListener {
                if (isPermission) {
                    openPhotoPicker()
                } else {
                    checkUserPermission(cameraStoragePermissions())
                }
            }
            email.afterTextChanged {
                if (!RegexUtils.isValidEmail(email.text.toString())) {
                    email.setError(resources.getString(R.string.email_invalid_error))
                    email.requestFocus()
                    vm.isValid.value = false
                } else if (!validLength(name.text.toString())) {
                    name.setError(resources.getString(R.string.textlimit_error))
                    name.requestFocus()
                    vm.isValid.value = false
                } else {
                    vm.isValid.value = true
                }
            }
            submit.setOnClickListener {
                if (name.text.toString().isEmpty()) {
                    name.setError(resources.getString(R.string.text_empty))
                    name.requestFocus()
                } else if (email.text.toString().isEmpty()) {
                    email.setError(resources.getString(R.string.text_empty))
                    email.requestFocus()
                } else if (phone.text.toString().isEmpty()) {
                    phone.setError(resources.getString(R.string.text_empty))
                    phone.requestFocus()
                } else if (phone.text.toString().length < 10) {
                    phone.setError(resources.getString(R.string.phonelimit_error))
                    phone.requestFocus()
                } else if (file == null) {
                    showToast("Oops!! Please choose image")
                } else {
                    if (vm.isValid.value!!) {
                        var data = SignupData()
                        data.name = phone.text.toString()
                        data.email = phone.text.toString().trim()
                        data.phone = phone.text.toString().trim()
                        data.image = file?.absolutePath.toString()
                        GlobalScope.launch(Dispatchers.IO) {
                            vm.sumbitData(data)
                        }
                    } else {
                        showToast(resources.getString(R.string.something_went_wrong))
                    }
                }
            }
        }
    }

    override fun observeViewModel() {
        vm.signupResult.observe(this@SignupScreen) {
            when (it) {
                is Result.Loading -> {
                    progress.show()
                }
                is Result.Success -> {
                    showToast(it.data)
                     startActivity(Intent(this@SignupScreen,ShowMassage::class.java))
                }
                is Result.LocalError -> {
                    showToast(it.localError)
                }
                is Result.Error -> {
                    showToast(resources.getString(R.string.something_went_wrong))
                }
            }
        }
    }

    ///// pic image
    fun openPhotoPicker() {
        val intent = Lassi(this)
        intent.with(LassiOption.CAMERA_AND_GALLERY)
        intent.setMediaType(MediaType.IMAGE) // MediaType : VIDEO IMAGE, AUDIO OR DOC
        intent.setMaxCount(1)
        intent.setGridSize(3)
        intent.setPlaceHolder(com.lassi.R.drawable.ic_image_placeholder)
        intent.setErrorDrawable(com.lassi.R.drawable.ic_image_placeholder)
        intent.setSelectionDrawable(com.lassi.R.drawable.ic_checked_media)
        intent.setStatusBarColor(com.lassi.R.color.colorPrimaryDark)
        intent.setToolbarColor(R.color.appcolor)
        intent.setToolbarResourceColor(android.R.color.white)
        intent.setProgressBarColor(R.color.black)
        intent.disableCrop()
        intent.setMinFileSize(0)
        intent.setMaxFileSize(2000)
        intent.enableFlip()
        intent.enableRotate()
        receiveData.launch(intent.build())
    }

    var file: File? = null

    private val receiveData =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val selectedMedia =
                    it.data?.getSerializableExtra(KeyUtils.SELECTED_MEDIA) as ArrayList<MiMedia>
                if (!selectedMedia.isNullOrEmpty()) {
                    val filePath = selectedMedia[0].path!!
                    file = File(filePath)
                    val mediaType =
                        "image/" + filePath!!.substring(filePath.lastIndexOf('.') + 1).trim()
                    binding.profileIcon.setImageURI(Uri.fromFile(file))
                }
            }
        }


}