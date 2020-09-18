package com.example.focus_now.repository


import com.example.focus_now.database.dao.TarefasDAO
import com.example.focus_now.model.Tarefa
import io.reactivex.Maybe.just
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TarefaRepositoryTest {

    @Spy
    private val dao: TarefasDAO? = null

    @Test
    fun deve_SalvarTarefa_QuandoInformadoATarefa() {

        val tarefas = Tarefa(descricao = "Tarefas")
        `when`(dao?.salvarTarefas(tarefas)).thenReturn(1L)
        TarefasRepository(dao!!).salvarTarefasBancoLocal(tarefas)

        verify(dao).salvarTarefas(tarefas)
    }

    @Test
    fun deve_Notificar_QuandoInserirNoBancoDeDados() {
        val tarefas = Tarefa(descricao = "Tarefas 1")
        val repository = TarefasRepository(dao!!)
        `when`(dao.salvarTarefas(tarefas)).thenReturn(1L)

        val resultadoRecebido = repository.salvarTarefasBancoLocal(tarefas)

        val resultadoEsperado = just(Resource(1L))
        resultadoEsperado.blockingGet()

        verify(dao).salvarTarefas(tarefas)

        assertEquals(resultadoEsperado.blockingGet(), resultadoRecebido.blockingGet())
    }

    @Test
    fun deve_EditarTarefas_QuandoRecebeUmaTarefaJaCadastrada() {
        val tarefas = Tarefa(id = 1, descricao = "Tarefas")
        doNothing().`when`(dao)?.editarTarefas(tarefas)

        val repository = TarefasRepository(dao!!)

        repository.salvarTarefasBancoLocal(tarefas)
        verify(dao).editarTarefas(tarefas)

        verify(dao, never()).salvarTarefas(tarefas)

    }

    @Test
    fun deve_DeletarTarefas_QuandoRecebeUmIDdaTarefa(){
        val tarefas = Tarefa(1,"Teste 1")

        tarefas.id?.let { doNothing().`when`(dao)?.excluirTarefas(it) }

        val repository = TarefasRepository(dao!!)

        repository.deletarTarefasDoBancoLocal(tarefas)
        tarefas.id?.let { verify(dao).excluirTarefas(it) }
    }


}