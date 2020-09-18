package com.example.focus_now.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StateAppViewModel : ViewModel() {

    val componentLiveData: LiveData<VisualComponents> get() = _components

    private var _components: MutableLiveData<VisualComponents> = MutableLiveData<VisualComponents>().also{
        it.value = hasComponent
    }

    var hasComponent: VisualComponents = VisualComponents()
    set(value) {
        field = value
        _components.value = value
    }


}

class VisualComponents(val appBar: Boolean = false, val title : Boolean = false, val floatActionButton: Boolean = false )
