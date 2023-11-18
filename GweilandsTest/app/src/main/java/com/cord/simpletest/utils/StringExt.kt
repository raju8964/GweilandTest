package com.cord.simpletest.utils
fun validLength(value: String): Boolean {
    if (value.length < 3) {
        return false
    } else {
        return true
    }

}