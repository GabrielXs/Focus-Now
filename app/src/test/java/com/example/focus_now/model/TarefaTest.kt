package com.example.focus_now.model


import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertEquals
import org.junit.Test

import java.util.*


internal class TarefaTest {

    private val tarefas = Tarefa(1, "Tarefa A")

    @Test
    fun deve_AdicionarSubTarefas_QuandoIncluirNovasTarefas() {
        val subTarefas = SubTarefas(1, "SubTarefas 1")
        tarefas.adicionaSubTarefas(subTarefas)
        assertEquals(1, tarefas.subTarefas?.size)
    }

    @Test
    fun deve_AdicionarSubTarefa_QuandoIncluirDuasNovasTarefas() {
        tarefas.adicionaSubTarefas(
            SubTarefas(1, "SubTarefas 1"),
            SubTarefas(2, "SubTarefas 2")
        )

        assertEquals(2, tarefas.subTarefas?.size)
    }

    @Test
    fun deve_RemoverASubTarefa_QuandoRetiraSubTarefaDaLista() {
        tarefas.adicionaSubTarefas(
            SubTarefas(1, "SubTarefas 1"),
            SubTarefas(2, "SubTarefas 2")
        )


        tarefas.removeSubTarefa(SubTarefas(2, "SubTarefas 2"))
        assertEquals(1, tarefas.subTarefas?.size)
    }

    @Test
    fun deve_EditarDescricao_QuandoAlterarDescricao() {
        tarefas.descricao = "Tarefa Teste"
        assertEquals("Tarefa Teste", tarefas.descricao)
    }

    @Test
    fun deve_adicionarAoMeuDia_QuandoForColocadoNoMeuDia() {
        tarefas.meuDia = MeuDia(true)
        assertEquals(true, tarefas.meuDia.meuDia)

    }


    @Test(expected = IllegalArgumentException::class)
    fun deve_NegarInsercaoLembrarMe_QuandoInformarDataHoraAnteriorMenorQueODiaAtual() {
        val calendar = Calendar.getInstance()
        calendar.set(2019, 7, 3)
        tarefas.lembrarMe = calendar.time
    }

    @Test
    fun deve_InserirADataDeConclusao_QuandoInformarAData() {
        val dataConclusao = Date()
        tarefas.dataConclusao = dataConclusao
        assertEquals(dataConclusao, tarefas.dataConclusao)

    }

    @Test(expected = IllegalArgumentException::class)
    fun naodeve_InserirDataDeConclusao_QuandoInformarDataAnterioQueDiaAtual() {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(2019, 7, 3)
        tarefas.dataConclusao = calendar.time
    }


    @Test
    fun deve_ColocarOrdemExecucao_QuandoInserirSubTarefas() {
        tarefas.adicionaSubTarefas(
            SubTarefas(1, "SubTarefas 1"),
            SubTarefas(2, "SubTarefas 2"),
            SubTarefas(3, "SubTarefas 3"),
            SubTarefas(4, "SubTarefas 4"),
            SubTarefas(5, "SubTarefas 4")
        )

        assertEquals(5, tarefas.subTarefas?.last()?.ordemExecucao)

    }


    @Test(expected = java.lang.IllegalArgumentException::class)
    fun naoDeve_OrdenarLista_QuandoInformadoUmaListaMaiorQueOriginal() {
        tarefas.adicionaSubTarefas(
            SubTarefas(1, "Tarefa 1"),
            SubTarefas(2, "Tarefa 2"),
            SubTarefas(3, "Tarefa 3")
        )

        tarefas.ordenarLista(
            SubTarefas(1, "Tarefa 1"),
            SubTarefas(2, "Tarefa 3"),
            SubTarefas(3, "Tarefa 2"),
            SubTarefas(4, "Tarefa 2")
        )
    }

    @Test
    fun deve_OrdenarLista_QuandoInformadoAOrdemQueAListaVaiSeguir() {
        tarefas.adicionaSubTarefas(
            SubTarefas(1, "Tarefa 1"),
            SubTarefas(2, "Tarefa 2"),
            SubTarefas(3, "Tarefa 3"),
            SubTarefas(4, "Tarefa 2")
        )


        val listaRecebidaOrdendada = tarefas.ordenarLista(
            SubTarefas(1, "Tarefa 1"),
            SubTarefas(3, "Tarefa 3"),
            SubTarefas(2, "Tarefa 2"),
            SubTarefas(4, "Tarefa 2")
        )


        assertThat(
            listaRecebidaOrdendada,
            IsEqual(
                listOf(
                    SubTarefas(1, "Tarefa 1", ordemExecucao = 1),
                    SubTarefas(3, "Tarefa 3", ordemExecucao = 2),
                    SubTarefas(2, "Tarefa 2", ordemExecucao = 3),
                    SubTarefas(4, "Tarefa 2", ordemExecucao = 4)
                )
            )
        )

    }

    @Test(expected = IllegalArgumentException::class)
    fun naoDeve_OrdenarLista_QuandoIdEDescricaoNaoCombinam() {
        tarefas.adicionaSubTarefas(
            SubTarefas(1, "Tarefa 1"),
            SubTarefas(2, "Tarefa 2"),
            SubTarefas(3, "Tarefa 3"),
            SubTarefas(4, "Tarefa 2")
        )



        tarefas.ordenarLista(
            SubTarefas(1, "Tarefa 1"),
            SubTarefas(2, "Tarefa 3"),
            SubTarefas(3, "Tarefa 2"),
            SubTarefas(4, "Tarefa 2")
        )

    }

    @Test
    fun deve_atualizarPomodoro_QuandoInformadoasSubTarefas() {
        tarefas.adicionaSubTarefas(
            SubTarefas(1, "Teste 1", pomodori = 1),
            SubTarefas(2, "Teste 2", pomodori = 3),
            SubTarefas(4, "Teste 3", pomodori = 4)
        )

        assertEquals(8, tarefas.pomodoro)
    }

    @Test
    fun `Deve mostrar a quantidade de tarefas que foi concluida`() {
        tarefas.adicionaSubTarefas(
            SubTarefas(1, "Tarefa 1").apply { this.concluido = true },
            SubTarefas(2, "Tarefa 2"),
            SubTarefas(3, "Tarefa 3").apply { this.concluido = true },
            SubTarefas(4, "Tarefa 2")
        )

        assertEquals(2, tarefas.quantidadeSubTarefasConcluidas())
    }

}