package com.ecarx.check.name

import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/*
 * 命名检查器基类,便于后续功能拓展
 */
 interface BaseNameCheck {
     fun visitType(e: TypeElement, p: Void?): Void? {
        return null
    }

     fun visitExecutable(e: ExecutableElement, p: Void?): Void? {
        return null
    }

     fun visitVariable(e: VariableElement, p: Void?): Void? {
        return null
    }
}