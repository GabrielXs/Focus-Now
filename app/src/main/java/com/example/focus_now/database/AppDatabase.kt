package com.example.focus_now.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.focus_now.database.converter.Converters
import com.example.focus_now.database.dao.TarefasDAO
import com.example.focus_now.model.Tarefa


@TypeConverters(Converters::class)
@Database(
    entities =
    [Tarefa::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val tarefasDAO: TarefasDAO
}