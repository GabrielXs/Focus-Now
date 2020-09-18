package com.example.focus_now.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.focus_now.R
import com.example.focus_now.extensions.toString
import com.example.focus_now.model.Tarefa
import com.example.focus_now.ui.recyclerview.TaskAdapter
import com.example.focus_now.ui.viewmodel.StateAppViewModel
import com.example.focus_now.ui.viewmodel.VisualComponents
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*

class MyDay : Fragment() {

    private val stateAppViewModel: StateAppViewModel by sharedViewModel()
    private val adapter by lazy {
        context?.let {
            TaskAdapter(it)
        } ?: throw IllegalArgumentException("Contexto inválido")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaTarefas()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateAppViewModel.hasComponent = VisualComponents(appBar = true,floatActionButton = true)
        configuraTela(view)
    }


    private fun configuraTela(view: View) {
        view.findViewById<TextView>(R.id.header_dia).apply {
            this.text = Date().toString("EEEE, dd MMM", Locale.getDefault())
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_task)
        recyclerView.adapter = adapter
        configuraAdapter()


    }

    private fun configuraAdapter() {
        adapter.iniciaTarefa = this::iniciarTarefaSeleciona
        adapter.editarTarefa = this::editarTarefa
    }

    private fun editarTarefa(tarefa: Tarefa, view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_tarefa, popupMenu.menu)
        val configurarCorVermelha = {
            val menuItem = popupMenu.menu.getItem(1)
            val title = menuItem.title
            val s = SpannableString(title.toString())
            s.setSpan(ForegroundColorSpan(Color.parseColor("#B00020")), 0, s.length, 0)
            menuItem.title = s
        }
        configurarCorVermelha()
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_editar_tarefa -> Toast.makeText(
                    context,
                    "Editar Tarefa",
                    Toast.LENGTH_LONG
                ).show()
                R.id.menu_excluir_tarefa -> Toast.makeText(
                    context,
                    "Excluir Tarefa",
                    Toast.LENGTH_LONG
                ).show()
            }
            true
        }
        popupMenu.show()
    }

    private fun iniciarTarefaSeleciona(tarefa: Tarefa) {
        Toast.makeText(context, "Iniciando Tarefa", Toast.LENGTH_LONG).show()
    }

    private fun buscaTarefas() {
        val tarefas: MutableList<Tarefa> = mutableListOf()
        for (i in 1..5) {
            tarefas.add(Tarefa(i.toLong(), "Tarefa $i"))
        }
        adapter.atualizaLista(tarefas)
    }
}