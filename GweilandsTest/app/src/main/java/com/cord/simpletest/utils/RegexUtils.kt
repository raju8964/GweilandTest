package com.cord.simpletest.utils


import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import java.util.regex.Matcher
import java.util.regex.Pattern


object RegexUtils {
     val EMAIL_ADDRESS: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )


     fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && EMAIL_ADDRESS.matcher(email).matches()
    }
}