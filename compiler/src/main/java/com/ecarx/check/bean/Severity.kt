package com.ecarx.check.bean

sealed class Severity{
    object INFO : Severity()
    object WARN :Severity()
    object ERROR:Severity()
}
