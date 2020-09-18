package com.example.focus_now.model

import java.util.*


data class Repeticao(val status: RepeticaoEnum = RepeticaoEnum.SEM_REPETICAO,
                     val dataInicio: Date?= null,val diasASerRepetidos : List<Calendar>? = null )


enum class RepeticaoEnum {
    DIARIAMENTE,
    DIAS_UTEIS,
    SEMANALMENTE,
    MENSALMENTE,
    ANUALMENTE,
    PERSONALIZAR,
    SEM_REPETICAO

}
