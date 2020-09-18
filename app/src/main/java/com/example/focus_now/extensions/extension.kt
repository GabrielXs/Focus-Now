package com.example.focus_now.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toString(pattern : String, locale : Locale):String{
    val format = SimpleDateFormat(pattern, locale)
   return format.format(this)
}