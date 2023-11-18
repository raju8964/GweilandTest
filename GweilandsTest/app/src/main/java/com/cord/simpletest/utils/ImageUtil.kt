package com.cord.simpletest.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.widget.ImageView
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File

fun ImageView.loadFromFile(file: File, defaultImage: Int) {
    if (file.exists()) {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(defaultImage)
        requestOptions.error(defaultImage)
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(this.context)
            .load(file)
            .apply(requestOptions)
            .into(this)
    } else {
        setImageResource(defaultImage)
    }

}