package com.example.focus_now.model

import androidx.room.ColumnInfo
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class Categoria (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_Categoria")
    val id:Long? = null,
    @ColumnInfo(name = "categoria")
    val descricao:String? = null,
    @ColumnInfo
    val icone: String? = null
)
