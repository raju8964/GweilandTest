package com.cord.simpletest

import android.app.Application
import com.cord.simpletest.data.SharedPref
import com.cord.simpletest.data.repo.MainRepo
import com.cord.simpletest.data.room.MyDatabase

class App : Application() {

    lateinit var quoteRepository: MainRepo

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {

       val database = MyDatabase.getInstance(applicationContext).mytask()
        SharedPref.getInstance(applicationContext)
        quoteRepository = MainRepo(database)
    }
}
