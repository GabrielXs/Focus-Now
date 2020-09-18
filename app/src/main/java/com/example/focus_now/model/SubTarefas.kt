package com.example.focus_now.model



data class SubTarefas(
   val id : Long ,
   val descricao: String,
   val pomodori:Int = 1,
   var concluido:Boolean = false,
   var ordemExecucao: Int = 1
)
