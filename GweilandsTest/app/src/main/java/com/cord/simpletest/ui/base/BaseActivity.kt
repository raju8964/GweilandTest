package com.cord.simpletest.ui.base


import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cord.simpletest.BuildConfig
import com.cord.simpletest.R
import com.cord.simpletest.ui.components.register.SignupVm
import com.cord.simpletest.utils.MProgressBar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


abstract class BaseActivity : AppCompatActivity() {
    abstract fun observeViewModel()
    protected abstract fun initViewBinding()
    lateinit var progress: Dialog
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        observeViewModel()
        updateStatusBarColorDrawer(true)
        progress = MProgressBar().showDialog(this)
        checkUserPermission(cameraStoragePermissions())
    }

    private fun updateStatusBarColorDrawer(
        isStatusBarFontDark: Boolean = true,
    ) {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.appcolor)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        window.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.white))
        setSystemBarTheme(isStatusBarFontDark)
    }

    private fun setSystemBarTheme(isStatusBarFontDark: Boolean) {
        window.decorView.systemUiVisibility =
            if (isStatusBarFontDark) {
                0
            } else {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
    }

    inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
        startActivity(Intent(this, T::class.java).apply(block))
    }

    inline fun <reified T : Activity> Context.startActivityWithFinish(block: Intent.() -> Unit = {}) {
        startActivity(Intent(this, T::class.java).apply(block))
        finish()
    }

    fun hideKeyBoard(input: View?) {
        input?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(input.windowToken, 0)
        }
    }

    private fun hideKeyBoard(): Boolean {
        try {
            if (currentFocus != null) {
                val inputMethodManager =
                    this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?

                inputMethodManager?.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    private var toast: Toast? = null
    fun showToast(message: String?) {
        hideKeyBoard()
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    var isPermission = false

    // check permission
    //// run time permission
    ///  @AfterPermissionGranted(Constants.RC_CAMERA_AND_STORAGE)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun cameraStoragePermissions(): Array<String> {
        var perms: Array<String>
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            perms = storge_permissions_33;
        } else {
            perms = storge_permissions;
        }
        return perms
    }

    var storge_permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    var storge_permissions_33 = arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.CAMERA,
    )
    fun checkUserPermission(perms: Array<String>) {
        Dexter.withActivity(this)
            .withPermissions(
                *perms
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                    if (report.areAllPermissionsGranted()) {
                        isPermission = true
                    } else {
                        val responses: List<PermissionDeniedResponse> =
                            report.getDeniedPermissionResponses()
                        val permissionsDenied = StringBuilder("Permissions denied: ")
                        for (response in responses) {
                            permissionsDenied.append(response.getPermissionName()).append(" ")
                        }

                    }
                    if (report.isAnyPermissionPermanentlyDenied()) {
                        rationalPermisson(this@BaseActivity)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()
    }

    fun rationalPermisson(context: Context) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setMessage(context.getString(R.string.camera_and_storage_rationale))
        alertDialog.setPositiveButton(context.getString(R.string.title_settings)) { _, _ ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

        }

        alertDialog.setNegativeButton(context.getString(R.string.okay)) { text, listener ->
            alertDialog.setCancelable(true)
        }
        alertDialog.create().show()

    }

}