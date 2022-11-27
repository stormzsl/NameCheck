package com.ecarx.check

import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.tools.Diagnostic

class Logger(private val _messager: Messager) {
    private fun printMessage(kindType: Diagnostic.Kind, msg: String?, element: Element?) {
        if (msg == null || element == null) {
            return
        }
        _messager.printMessage(kindType, msg, element)
    }

    fun info(msg: String?, element: Element?) {
        printMessage(Diagnostic.Kind.NOTE, msg, element)
    }

    fun error(msg: String?, element: Element?) {
        printMessage(Diagnostic.Kind.ERROR, msg, element)
    }

    fun warn(msg: String?, element: Element?) {
        printMessage(Diagnostic.Kind.WARNING, msg, element)
    }
}