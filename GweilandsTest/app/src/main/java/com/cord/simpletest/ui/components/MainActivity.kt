package com.cord.simpletest.ui.components

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cord.simpletest.App
import com.cord.simpletest.ui.components.chatscreen.adapter.adapters
import com.cord.simpletest.databinding.ActivityMainBinding
import com.cord.simpletest.models.Image
import com.cord.simpletest.data.room.MyDatabase
import com.cord.simpletest.ui.components.chatscreen.ShowMassage
import com.cord.simpletest.data.viewModel.MainViewModel
import com.cord.simpletest.data.viewModel.MainViewModelFactory
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var database: MyDatabase
    lateinit var mainViewModel: MainViewModel
    var count=0;
    var adapter: adapters?=null
    var mlist=ArrayList<Image>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ///checkPermission()
        rutimePermission()
        database= MyDatabase.getInstance(this)
        val repository = (application as App).quoteRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
        binding.apply {
            recyclervie.setHasFixedSize(true)
            recyclervie.layoutManager=LinearLayoutManager(this@MainActivity)
            adapter = adapters(this@MainActivity,mlist) {
                startActivity(Intent(this@MainActivity, ShowMassage::class.java)
                    .putExtra("userimage", mlist?.get(it)?.xt_image)
                )
            }
            recyclervie.adapter=adapter

    }}



    private val PERMISSION_REQUEST_CODE = 200

    private fun checkPermission(): Boolean {

        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                }
            }).check()
    return  true
    }


    private fun rutimePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    101
                )
            }
        } else {
            // Permission has already been granted
        }
    }

}
