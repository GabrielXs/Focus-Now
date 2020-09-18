package com.example.focus_now.model

data class Pomodoro(private val tarefa: Tarefa, private val status: StatusPomodoro) {


    enum class StatusPomodoro {
        INICIADO,
        PAUSADO,
        PARADO,
        PAUSE_BREAK,
        LONG_BREAK
    }
}