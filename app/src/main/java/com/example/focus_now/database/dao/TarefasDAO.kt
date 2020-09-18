package com.example.focus_now.database.dao

import androidx.room.*
import com.example.focus_now.model.Tarefa

@Dao
interface TarefasDAO {
    @Insert
    fun salvarTarefas(tarefa: Tarefa): Long

    @Update
    fun editarTarefas(tarefa: Tarefa)

    @Query("DELETE FROM Tarefa WHERE id = :id ")
    fun excluirTarefas(id: Long)

    @Query("SELECT * FROM Tarefa")
    fun buscarTodosAsTarefas(): List<Tarefa>

    @Query("SELECT * FROM Tarefa WHERE id = :id ")
    fun getTarefas(id: Long) : Tarefa




}