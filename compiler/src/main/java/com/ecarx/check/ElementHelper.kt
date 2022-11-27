package com.ecarx.check

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

object ElementHelper {
    lateinit var elementsUtils: Elements
    lateinit var typeUtils: Types
    lateinit var logger: Logger

    fun init(environment: ProcessingEnvironment) {
        elementsUtils = environment.elementUtils
        typeUtils = environment.typeUtils
        logger = Logger(environment.messager)
    }
}