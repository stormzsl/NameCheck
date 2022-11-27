package com.ecarx.check.name

import com.ecarx.check.checkAllCaps
import com.ecarx.check.checkCamelCase
import com.ecarx.check.utils.checkPrivateVariableElement
import com.ecarx.check.utils.isPrivateVariableElement
import com.ecarx.check.utils.variableIsConstant
import com.ecarx.check.utils.variableIsEnum
import javax.lang.model.element.VariableElement

class VariableNameCheck : BaseNameCheck {
    override fun visitVariable(e: VariableElement, p: Void?): Void? {
        //如果这个Variable是枚举或常量，则按大写命名检查，否则按照驼式命名法规则检查
        if (variableIsConstant(e) || variableIsEnum(e)) {
            checkAllCaps(e)
        } else {
            checkCamelCase(e, false)//按照驼式命名法规则检查
        }
        return super.visitVariable(e, p)
    }
}