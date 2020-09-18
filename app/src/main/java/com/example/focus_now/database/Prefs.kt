package com.example.focus_now.database

import android.content.Context


private const val PREF_NAME = "focus_pref"
abstract class Prefs {


    companion object{
        fun getValorMinimoPomodori(context: Context):Int{
            return context.getSharedPreferences(PREF_NAME,0).getInt("MINIMO_POMODORI",5)
        }
        fun setValorMinimoPomodori(context: Context, value:Int){
            context.getSharedPreferences(PREF_NAME,0).edit().apply {
                putInt("MINIMO_POMODORI", value)
            }.apply()
        }
    }
}