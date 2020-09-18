package com.example.focus_now.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.focus_now.database.dao.TarefasDAO
import com.example.focus_now.model.Tarefa
import com.example.focus_now.util.exception.AlertException
import io.reactivex.Maybe
import java.text.SimpleDateFormat
import java.util.*

class TarefasRepository(private val dao: TarefasDAO) {
    fun salvarTarefasBancoLocal(tarefa: Tarefa): Maybe<Resource<Long>> {
        return try {
            return if (tarefa.id == null) {
                val idTarefas = dao.salvarTarefas(tarefa)
                Maybe.just(Resource(idTarefas))
            } else {
                dao.editarTarefas(tarefa)
                Maybe.just(Resource(tarefa.id))
            }
        } catch (ex: Exception) {
            Maybe.error(ex)
        }
    }

    fun deletarTarefasDoBancoLocal(tarefa: Tarefa): Maybe<Resource<Any>> {
        return try {
            tarefa.id?.let { dao.excluirTarefas(it) }
            Maybe.just(Resource<Any>(true))
        } catch (ex: Exception) {
            Maybe.error(ex)
        }
    }

    fun getTarefasMeuDia(
        tarefasAtrasadas: Boolean,
        ignoraRegra: Boolean
    ): Maybe<Resource<List<Tarefa>>> {
        return try {
            val listaTarefas = dao.buscarTodosAsTarefas()
            verificarTarefasAtrasadas(listaTarefas, tarefasAtrasadas, ignoraRegra)

            val tarefasMeuDia = listaTarefas.filter {
                val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val data = format.format(it.meuDia.dataCriacao)
                data == format.format(Date()) && it.meuDia.meuDia
            }
            Maybe.just(Resource(tarefasMeuDia))
        } catch (ex: Exception) {
            Maybe.error(ex)
        }
    }

    private fun verificarTarefasAtrasadas(
        tarefas: List<Tarefa>,
        trazertarefasAtrasadas: Boolean,
        ignoraRegra: Boolean
    ) {
        val tarefasAtrasadas = tarefas.filter {
            val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            format.format(it.meuDia.dataCriacao) != format.format(Date())

        }

        if (trazertarefasAtrasadas)
            tarefasAtrasadas.forEach { it.meuDia.dataCriacao = Date() }

        if (!ignoraRegra)
            if (tarefasAtrasadas.count() > 0) throw AlertException("Existem Tarefas Atrasadas \n Deseja trazer as Tarefas para Hoje?")

    }

    fun getTarefa(id: Long): LiveData<Tarefa> {
        val tarefaEncontrada =  dao.getTarefas(id)
        return MutableLiveData<Tarefa>().apply { value = tarefaEncontrada}
    }


}