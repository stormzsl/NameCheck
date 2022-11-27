package com.ecarx.check.name

import com.ecarx.check.checkCamelCase
import javax.lang.model.element.TypeElement

/*
 * 类/接口命名检查器
 * 符合驼峰命名规则：首字母需要大写
 */
class TypeNameCheck : BaseNameCheck {
    override fun visitType(e: TypeElement, p: Void?): Void? {
        checkCamelCase(e, true)
        return super.visitType(e, p)
    }
}