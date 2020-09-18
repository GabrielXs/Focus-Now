package com.example.focus_now.ui.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.focus_now.database.Prefs
import com.example.focus_now.database.dao.TarefasDAO
import com.example.focus_now.model.MeuDia
import com.example.focus_now.model.Tarefa
import com.example.focus_now.repository.Resource
import com.example.focus_now.repository.TarefasRepository
import com.example.focus_now.util.exception.AlertException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Maybe
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.*

internal class TarefaViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<TarefasRepository>()
    private val context = mockk<Context>()

    private val viewModel = TarefasViewModel(repository)
    private val tarefas = Tarefa(descricao = "Tarefa")

    @Test
    fun `Deve continuar a gravacao da tarefas quando o usuario informa que deseja continuar `() {
        val retorno = Maybe.just(Resource<Long>(1L))
        val observer = mockk<Observer<Maybe<Resource<Long>>>>(relaxed = true)

        every { repository.salvarTarefasBancoLocal(tarefas) } returns retorno

        viewModel.salvarTarefa(context, tarefas, true).observeForever(observer)

        verify {
            repository.salvarTarefasBancoLocal(tarefas)
        }
        verify {
            observer.onChanged(retorno)
        }


    }


    @Test(expected = AlertException::class)
    fun `Deve aconselhar o usuario a criar mais subtarefas quando informado um valor para o pomodoro maior que o definido nas configuracoes `() {
        tarefas.pomodoro = 6
        every { Prefs.getValorMinimoPomodori(context) } returns 5

        val maybe = viewModel.salvarTarefa(context, tarefas).value
        maybe!!.blockingGet()
    }


    @Test
    fun `Deve receber o id da tarefas quando informado o objeto para salvar`() {
        val retorno = Maybe.just(Resource<Long>(1L))
        val observer = mockk<Observer<Maybe<Resource<Long>>>>(relaxed = true)

        every { Prefs.getValorMinimoPomodori(context) } returns 5
        every { repository.salvarTarefasBancoLocal(tarefas) } returns retorno

        viewModel.salvarTarefa(context, tarefas).observeForever(observer)

        verify { repository.salvarTarefasBancoLocal(tarefas) }
        verify {
            observer.onChanged(retorno)
        }

    }


    @Test
    fun `Deve excluir a tarefa quando solicitado pelo usuario `() {
        val retorno = Maybe.just(Resource<Any>(true))
        val observer = mockk<Observer<Maybe<Resource<Any>>>>(relaxed = true)

        every { repository.deletarTarefasDoBancoLocal(tarefas) } returns retorno

        viewModel.deletarTarefa(tarefas).observeForever(observer)

        verify { observer.onChanged(retorno) }

    }

    @Test
    fun `Deve trazer todas as tarefas definida para meu dia que nao estao atrasadas`() {
        val listaTarefas: MutableList<Tarefa> = mutableListOf()
        for (i in 1..5) {
            val tarefa = Tarefa(descricao = "Teste $i")
            if(i % 2 == 0) {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.DATE, 10)
                tarefa.meuDia = MeuDia(true, calendar.time)
            }else{
                tarefa.meuDia = MeuDia(true)
            }
            listaTarefas.add(tarefa)
        }
        val dao = mockk<TarefasDAO>()
        val repository = TarefasRepository(dao)
        val viewModel = TarefasViewModel(repository)

        every { dao.buscarTodosAsTarefas() } returns listaTarefas

        val valorRecebido = viewModel.getTarefasDoMeuDia(ignoraRegra = true).value

        verify {
            dao.buscarTodosAsTarefas()
        }

        Assert.assertEquals(3, valorRecebido!!.blockingGet().data.count())


    }

    @Test(expected = AlertException::class)
    fun `Deve notificar para o usuario que existe tarefas que nao foi concluida no dia anterior`(){
        val listaTarefas: MutableList<Tarefa> = mutableListOf()
        for (i in 1..5) {
            val tarefa = Tarefa(descricao = "Teste $i")
            if(i % 2 == 0) {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.DATE, 10)
                tarefa.meuDia = MeuDia(true, calendar.time)
            }else{
                tarefa.meuDia = MeuDia(true)
            }
            listaTarefas.add(tarefa)
        }

        val dao = mockk<TarefasDAO>()
        val repository = TarefasRepository(dao)
        val viewModel = TarefasViewModel(repository)

        every { dao.buscarTodosAsTarefas() } returns listaTarefas

        val maybe = viewModel.getTarefasDoMeuDia().value
        maybe!!.blockingGet()
    }

    @Test
    fun `Deve trazer tarefas atrasadas do meu dia com datas atual `(){
        val listaTarefas: MutableList<Tarefa> = mutableListOf()
        for (i in 1..5) {
            val tarefa = Tarefa(descricao = "Teste $i")
            if(i % 2 == 0) {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.DATE, 10)
                tarefa.meuDia = MeuDia(true, calendar.time)
            }else{
                tarefa.meuDia = MeuDia(true)
            }
            listaTarefas.add(tarefa)
        }

        val dao = mockk<TarefasDAO>()
        val repository = TarefasRepository(dao)
        val viewModel = TarefasViewModel(repository)

        every { dao.buscarTodosAsTarefas() } returns listaTarefas


        val valorRecebido = viewModel.getTarefasDoMeuDia(trazerTarefasAtrasadas = true,ignoraRegra = true).value

        verify {
            dao.buscarTodosAsTarefas()
        }

        Assert.assertEquals(5, valorRecebido!!.blockingGet().data.count())

    }


    @Test
    fun `Deve trazer a tarefa informado quando informado o ID`(){
        val listaTarefas: MutableList<Tarefa> = mutableListOf()
        for (i in 1..5){
            val tarefas = Tarefa(i.toLong(), "Teste $i")
            listaTarefas.add(tarefas)
        }

        val dao = mockk<TarefasDAO>()
        val repository = TarefasRepository(dao)
        val viewModel = TarefasViewModel(repository)

        every { dao.getTarefas(2) } returns listaTarefas.find { it.id == 2L }!!


        val tarefaSelecionada = viewModel.getTarefas(2)

        verify {
            dao.getTarefas(2)
        }

        Assert.assertEquals(2L , tarefaSelecionada.value?.id)
    }


}