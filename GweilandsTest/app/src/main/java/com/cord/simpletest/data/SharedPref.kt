package com.cord.simpletest.data
import android.content.Context
import android.content.SharedPreferences

import com.cord.simpletest.utils.Common
import com.cord.simpletest.utils.decrypt
import com.cord.simpletest.utils.encrypt



class SharedPref private constructor() {

    companion object {
        private var editor: SharedPreferences.Editor? = null

        private var sharedPref: SharedPref? = null
        lateinit var pref: SharedPreferences
        fun getInstance(context: Context): SharedPref {
            if (pref == null) {
                synchronized(this) {
                    if (pref == null) {
                        sharedPref = SharedPref()
                        pref = context.getSharedPreferences(
                            Common.SharedPref_Table,
                            Context.MODE_PRIVATE
                        )
                    }
                }
            }
            return sharedPref!!
        }


        //// save data
        fun saveData(key: String?, value: String) {
            editor = pref!!.edit()
            editor!!.putString(key, encrypt(value)).apply()
            editor!!.commit()
        }

        /// getData
        fun getData(key: String): String {
            var value = ""
            try {
                return  pref.getString(key, "")!!
            }
            catch (e:Exception){
                return e.message.toString()
            }
        }

        //// clear data
        fun clearData() {
            if (editor != null) {
                editor!!.clear().commit()
            }
        }

        //// remove data
        fun removeData(value: String) {
            if (editor != null) {
                editor!!.remove(value)
                editor!!.commit();
            }
        }


    }
}