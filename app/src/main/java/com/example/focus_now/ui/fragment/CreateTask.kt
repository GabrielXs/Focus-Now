package com.example.focus_now.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.focus_now.R
import com.example.focus_now.ui.viewmodel.StateAppViewModel
import com.example.focus_now.ui.viewmodel.VisualComponents
import org.koin.android.viewmodel.ext.android.sharedViewModel


class CreateTask : Fragment() {

    private val stateAppViewModel: StateAppViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_create_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateAppViewModel.hasComponent =
            VisualComponents(appBar = true, title = true, floatActionButton = false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_task -> {
                Toast.makeText(context, "Salvar Tarefa", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_task, menu)

    }
}