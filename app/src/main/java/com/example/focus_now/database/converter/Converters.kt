package com.example.focus_now.database.converter

import androidx.room.TypeConverter
import com.example.focus_now.model.MeuDia
import com.example.focus_now.model.Repeticao
import com.example.focus_now.model.SubTarefas
import com.google.gson.Gson
import java.util.*

class Converters {

    @TypeConverter
    fun SubTarefasListToJsonSubTarefasList(subTarefas: MutableList<SubTarefas>): String {
        val lista = subTarefas
        return Gson().toJson(lista)

    }

    @TypeConverter
    fun JsonSubTarefasListToSubTarefasList(subTarefas: String): MutableList<SubTarefas>? {
        return Gson().fromJson<MutableList<SubTarefas>>(subTarefas, SubTarefas::class.java)

    }

    @TypeConverter
    fun DateToLong(data: Date?): Long? {
        data?.let {
            return it.time
        }
        return null
    }

    @TypeConverter
    fun LongToDate(data: Long?): Date? {
        data?.let {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = data
            return calendar.time
        }
        return null
    }

    @TypeConverter
    fun RepeticaoToJson(repeticao: Repeticao?): String? {
        repeticao?.let {
            return Gson().toJson(repeticao)
        }
        return null

    }

    @TypeConverter
    fun JsonToRepeticao(json: String?): Repeticao? {
        json?.let {
            return Gson().fromJson(json, Repeticao::class.java)
        }
        return null

    }

    @TypeConverter
    fun MeuDiaToJson(meuDia: MeuDia?): String?{
        meuDia?.let {
            return Gson().toJson(meuDia)
        }
        return null
    }

    @TypeConverter
    fun jsonToMeuDia(json: String?): MeuDia?{
        json?.let {
            return Gson().fromJson(json, MeuDia::class.java)
        }
        return null
    }


}