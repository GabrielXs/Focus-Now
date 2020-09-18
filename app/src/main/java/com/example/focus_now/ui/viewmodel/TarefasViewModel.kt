package com.example.focus_now.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.focus_now.database.Prefs
import com.example.focus_now.model.Tarefa
import com.example.focus_now.repository.Resource
import com.example.focus_now.repository.TarefasRepository
import com.example.focus_now.util.exception.AlertException
import io.reactivex.Maybe
import java.lang.Exception

private const val TAG = "TarefasViewModel"

class TarefasViewModel(private val repository: TarefasRepository) : ViewModel() {

    fun salvarTarefa(context: Context, tarefa: Tarefa, ignoraRegra: Boolean = false): LiveData<Maybe<Resource<Long>>> {
        val liveData = MutableLiveData<Maybe<Resource<Long>>>()
        try {
            if(!ignoraRegra) {
                verificarQuantidadePomodoro(context,tarefa)
            }
            val id = repository.salvarTarefasBancoLocal(tarefa)
             liveData.value = id

        }catch (ex : Exception){
            liveData.value = Maybe.error(ex)
        }
        return  liveData
    }

    private fun verificarQuantidadePomodoro(context: Context, tarefa: Tarefa) {
        val valorMinimoPomodoro = Prefs.getValorMinimoPomodori(context)
        if(tarefa.pomodoro > valorMinimoPomodoro && tarefa.subTarefas.isNullOrEmpty()){
            throw  AlertException(
                "Foi informado uma quantidade de Pomodoro muito alta é conselhavel criar sub tarefas para lhe ajudar\n" +
                        " Deseja continuar ?"
            )
        }
    }

    fun deletarTarefa(tarefa: Tarefa): LiveData<Maybe<Resource<Any>>> {
        return MutableLiveData<Maybe<Resource<Any>>>().apply {
            value = repository.deletarTarefasDoBancoLocal(tarefa)
        }
    }

    fun getTarefasDoMeuDia(trazerTarefasAtrasadas: Boolean = false, ignoraRegra: Boolean = false) : LiveData<Maybe<Resource<List<Tarefa>>>> {
        return  MutableLiveData<Maybe<Resource<List<Tarefa>>>>().apply {
            value = repository.getTarefasMeuDia(trazerTarefasAtrasadas,ignoraRegra)
        }
    }

    fun getTarefas(id: Long): LiveData<Tarefa> {
        return repository.getTarefa(id)
    }
}