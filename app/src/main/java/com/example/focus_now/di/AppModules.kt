package com.example.focus_now.di

import androidx.room.Room
import com.example.focus_now.database.AppDatabase
import com.example.focus_now.database.dao.TarefasDAO
import com.example.focus_now.ui.viewmodel.StateAppViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


const val NOME_BANCO_DADOS = "focus.db"

val appModules = module {

    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, NOME_BANCO_DADOS).build()
    }

    single<TarefasDAO> {
        get<AppDatabase>().tarefasDAO
    }


    viewModel<StateAppViewModel> {
        StateAppViewModel()
    }
}