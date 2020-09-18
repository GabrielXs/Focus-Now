package com.example.focus_now.extensions

import org.junit.Assert.*
import org.junit.Test
import java.util.*

internal class ExtensionKtTest{
    @Test
    fun `Deve trazer data em texto de acordo com Locale`(){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DATE,3)
        calendar.set(Calendar.MONTH,2)
        calendar.set(Calendar.YEAR,2020)
        val date = calendar.time

        val dataString = date.toString("EEE, dd MMMM yyyy",Locale.forLanguageTag("pt-BR"))
        assertEquals("Ter, 03 Março 2020",dataString)
    }
}