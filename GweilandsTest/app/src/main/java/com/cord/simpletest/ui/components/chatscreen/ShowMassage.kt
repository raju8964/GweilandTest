package com.cord.simpletest.ui.components.chatscreen

import android.app.Dialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.cord.simpletest.App import com.cord.simpletest.models.Image
import com.cord.simpletest.ui.base.BaseActivity
import com.cord.simpletest.ui.components.chatscreen.adapter.adapters
import com.cord.simpletest.data.viewModel.MainViewModel
import com.cord.simpletest.data.viewModel.MainViewModelFactory
import com.cord.simpletest.databinding.ActivityShowMessageBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.net.URL

class ShowMassage : BaseActivity() {
    lateinit var binding: ActivityShowMessageBinding
    lateinit var mainViewModel: MainViewModel

    var user_image = ""
    var dialog: Dialog? = null
    var file: File? = null
    var  madapter:adapters?=null
    var mList=ArrayList<Image>()


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)
        user_image = intent.getStringExtra("userimage").toString() + ""

        val repository = (application as App).quoteRepository
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
        binding.apply {
            userimage.setImageURI(Uri.parse(URL(user_image).toString()))
            send.setOnClickListener {
                mainViewModel.saveData(message.text.toString())
            }

        }
        recyclerUi()
    }

    private fun recyclerUi() {
        binding.apply {
        }
    }
    override  fun observeViewModel() {
        GlobalScope.launch {
            mainViewModel.getdata().observe(this@ShowMassage, {
                Log.e("akcnka","sacsakc"+it[0].message)

})}

    }

    override fun initViewBinding() {

    }


}