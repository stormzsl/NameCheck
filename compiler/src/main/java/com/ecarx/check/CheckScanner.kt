package com.ecarx.check

import com.ecarx.check.name.ExecutableNameCheck
import com.ecarx.check.name.TypeNameCheck
import com.ecarx.check.name.VariableNameCheck
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.ElementScanner8

/*
 * 命名规范检查具体实现类，以Visitor的方式访问AST语法树中的元素
 */
class CheckScanner : ElementScanner8<Void?, Void?>() {
    /*
     * ast访问类/接口节点触发
     */
    override fun visitType(e: TypeElement, p: Void?): Void? {
        scan(e.typeParameters, p)
        TypeNameCheck().visitType(e, p)
        super.visitType(e, p)
        return null
    }

    /**
     * ast访问方法节点触发
     */
    override fun visitExecutable(e: ExecutableElement, p: Void?): Void? {
        ExecutableNameCheck().visitExecutable(e, p)
        super.visitExecutable(e, p)
        return null
    }

    /**
     * ast访问成员变量，方法参数节点触发
     */
    override fun visitVariable(e: VariableElement, p: Void?): Void? {
        VariableNameCheck().visitVariable(e, p)
        return null
    }
}