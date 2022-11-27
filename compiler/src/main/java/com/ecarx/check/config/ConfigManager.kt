package com.ecarx.check.config

import com.ecarx.check.ANDROID_GENERATE_BUILD_CONFIG
import com.ecarx.check.ANDROID_GENERATE_R_JAVA
import com.ecarx.check.utils.getFullClassName
import com.ecarx.check.utils.getSimpleClassName
import java.io.File
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

class ConfigManager private constructor(configFilePath: String ? ="") {
    private var parser: ConfigParser

    companion object {
        private var instance: ConfigManager? = null
        fun getInstance(configFilePath: String ?): ConfigManager {
            if (instance == null) {
                instance = ConfigManager(configFilePath)
            }
            return instance!!
        }
    }

    init {
        val configFile = configFilePath?.let { File(it) }
        parser = configFile?.let { ConfigParser(it) }!!
    }

    val configApi by lazy {
        parser.getConfigApi()
    }

    val handleCheckNames by lazy {
        parser.getHandleCheckNames()
    }

    /*
     * 是否匹配exclude内容
     */
    fun matchExcludeElement(element: Element?): Boolean {
        if (element == null) {
            return false
        }
        if (element is TypeElement) {
            println("**** matchExcludeElement ${element.qualifiedName} ")
            return matchTypeElement(element)
        }
        return if (element.enclosingElement is TypeElement) {
            matchTypeElement(element.enclosingElement as TypeElement)
        } else false
    }

    // 判断是否是系统类如R.java，android.*
    private fun matchTypeElement(element: TypeElement): Boolean {
        if (getFullClassName(element).contains(ANDROID_GENERATE_R_JAVA)) {
            return true
        }

        if (getSimpleClassName(element) == ANDROID_GENERATE_BUILD_CONFIG) {
            return true
        }

        return configApi.exclude.any {
            println("**** matchTypeElement simpleClassName ${getSimpleClassName(element)} ")
            getFullClassName(element).startsWith(it.prefix)
        }
    }
}