package com.example.focus_now.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
open class Tarefa(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    @ColumnInfo
    var descricao: String
) {

    @ColumnInfo
    var subTarefas: MutableList<SubTarefas>? = mutableListOf()
    @Embedded
    var categoria: Categoria? = Categoria()
    @ColumnInfo
    var meuDia: MeuDia = MeuDia(false)
    @ColumnInfo
    var lembrarMe: Date? = null
        set(value) {
            value?.let {
                if (value.before(Date())) throw IllegalArgumentException("Data de conclusão não pode ser inferior que data atual")
            }
            field = value
        }

    @ColumnInfo
    var dataConclusao: Date? = null
        set(value) {
            value?.let {
                if (value.before(Date())) throw IllegalArgumentException("Data de conclusão não pode ser inferior que data atual")
            }
            field = value
        }

    @ColumnInfo
    var pomodoro: Int = 0
        set(value) {
            if (value < 0) throw IllegalArgumentException("Quantidade de Pomodoro inválida")
            field = value
        }

    @ColumnInfo
    var repetir = Repeticao()
    @ColumnInfo
    var anexo: String? = null
    @ColumnInfo
    var concluido: Boolean = false


    fun adicionaSubTarefas(vararg subTarefas: SubTarefas) {
        subTarefas.forEach {
            var ordem = this.subTarefas?.size ?: 0
            ordem++
            it.ordemExecucao = ordem
            this.pomodoro += it.pomodori
            this.subTarefas?.add(it)
        }

    }

    fun removeSubTarefa(tarefas: SubTarefas) {
        val tarefaAserRemovida =
            subTarefas?.find { it.id == tarefas.id && it.descricao == tarefas.descricao }
        subTarefas?.remove(tarefaAserRemovida)
    }




    fun ordenarLista(vararg subTarefas: SubTarefas): List<SubTarefas>? {
        if (subTarefas.size > this.subTarefas?.size ?: 0) throw IllegalArgumentException("Tarefas informada maior que original")

        for (i in subTarefas.indices) {
            val tarefas = subTarefas[i]
            var ordem = i
            ordem++
            val tarefasOrdenadas =
                this.subTarefas?.find { tarefas.id == it.id && tarefas.descricao == it.descricao }
            tarefasOrdenadas
                ?: throw IllegalArgumentException("Id informado não corresponde com a Descricao")
            tarefasOrdenadas.ordemExecucao = ordem

        }

        return this.subTarefas?.sortedBy { it.ordemExecucao }
    }

    fun quantidadeSubTarefasConcluidas() : Int{
        var count = 0
        subTarefas?.forEach {
            if (it.concluido){
                count ++
            }
        }
        return count
    }


}