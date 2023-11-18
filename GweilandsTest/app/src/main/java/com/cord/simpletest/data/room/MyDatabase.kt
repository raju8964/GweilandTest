package com.cord.simpletest.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cord.simpletest.ui.components.chatscreen.model.Task

@Database(entities = [Task::class], version = 1)
abstract class MyDatabase:RoomDatabase() {
  abstract  fun mytask(): MyDao
  companion object{
      @Volatile
      private var INSTANCE: MyDatabase?=null
      fun getInstance(context:Context): MyDatabase {
          if (INSTANCE ==null){
              synchronized(this){
              INSTANCE =Room.databaseBuilder(context, MyDatabase::class.java,
                  "TaskDb").build()
          }}
return INSTANCE!!
      }
  }
}