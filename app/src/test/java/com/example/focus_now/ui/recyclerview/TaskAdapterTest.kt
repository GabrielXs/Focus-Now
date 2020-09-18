package com.example.focus_now.ui.recyclerview

import android.content.Context
import com.example.focus_now.model.Tarefa
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Test

internal class TaskAdapterTest{


    private val context = mockk<Context>()

    @Test
    fun `Deve trazer a quantidade de tarefas quando informado uma certa quantidade`(){
        val tarefas : MutableList< Tarefa> = mutableListOf()
        for (i in  1..5) {
            tarefas.add(Tarefa(i.toLong() , "Tarefa $i"))
        }

        val myDayAdapter = TaskAdapter(context,tarefas)
        assertEquals(5, myDayAdapter.itemCount)
    }


    @Test
    fun `Deve atualizar o adapter quando retirado um item  `(){

        val tarefas : MutableList< Tarefa> = mutableListOf()
        for (i in  1..5) {
            tarefas.add(Tarefa(i.toLong() , "Tarefa $i"))
        }
        val myDayAdapter = spyk(TaskAdapter(context,tarefas))

        every {  myDayAdapter.atualizaLista(tarefas) } just Runs

        tarefas.remove(tarefas.last())
        myDayAdapter.atualizaLista(tarefas)

        verify {
            myDayAdapter.atualizaLista(tarefas)
        }

        assertEquals(4, myDayAdapter.itemCount)
    }
}