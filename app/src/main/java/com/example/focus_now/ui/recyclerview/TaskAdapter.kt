package com.example.focus_now.ui.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.focus_now.R
import com.example.focus_now.extensions.toString
import com.example.focus_now.model.Tarefa
import kotlinx.android.synthetic.main.lista_tarefas_item.view.*
import java.util.*

class TaskAdapter(
    private val context: Context,
    private val tarefas: MutableList<Tarefa> = mutableListOf(),
    var iniciaTarefa: (tarefa: Tarefa) -> Unit = {},
    var editarTarefa: (tarefa: Tarefa, viewAnchor: View) -> Unit = {_,_ ->}
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.lista_tarefas_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tarefas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarefa = tarefas[position]
        holder.vincula(tarefa)
    }


    fun atualizaLista(tarefas : MutableList<Tarefa>) {
        this.tarefas.clear()
        this.tarefas.addAll(tarefas)
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var tarefa: Tarefa


        fun vincula(tarefa: Tarefa) {
            this.tarefa = tarefa
            itemView.txt_descricao_tarefas.text = tarefa.descricao
            if (tarefa.subTarefas?.size ?: 0 > 0) {
                itemView.txt_quantidade_sub_tarefas.visibility = View.VISIBLE
                val tarefasConcluidas = tarefa.quantidadeSubTarefasConcluidas()
                itemView.txt_quantidade_sub_tarefas.text = String.format(
                    context.getString(R.string.qtde_concluido),
                    tarefasConcluidas,
                    tarefa.subTarefas?.size
                )
            }

            if (tarefa.categoria != null) {
                tarefa.categoria?.let {
                    itemView.txt_categoria_tarefa.visibility = View.VISIBLE
                    itemView.txt_categoria_tarefa.text = it.descricao
                }
            }

            itemView.btn_iniciando_tarefa.setOnClickListener { iniciaTarefa(tarefa) }

            tarefa.dataConclusao?.let { dataConclusao ->
                if (dataConclusao.before(Date()) && !tarefa.concluido) {
                    itemView.linear_tarefa_atrasada.visibility = View.VISIBLE
                    itemView.txt_data_atrasado.text =
                        dataConclusao.toString("EEE, dd MMMM yyyy", Locale.getDefault())
                }
            }

            itemView.card_tarefa.setOnLongClickListener {
                editarTarefa(tarefa, it)
                true
            }


        }

    }

}