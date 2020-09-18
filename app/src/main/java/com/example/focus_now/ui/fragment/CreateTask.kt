package com.example.focus_now.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return inflater.inflate(R.layout.fragment_create_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateAppViewModel.hasComponent = VisualComponents(appBar = true, title = true, floatActionButton = false)
    }
}