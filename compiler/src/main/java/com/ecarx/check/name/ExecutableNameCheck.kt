package com.ecarx.check.name

import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.ElementKind
import com.ecarx.check.ElementHelper
import com.ecarx.check.checkCamelCase

/* 方法命名检查器
 * 检查所有方法包括构造方法，命名规则是否符合驼峰规范，首字母小写
 */
class ExecutableNameCheck : BaseNameCheck {
    override fun visitExecutable(e: ExecutableElement, p: Void?): Void? {
        if (e.kind == ElementKind.METHOD) {
            val name = e.simpleName
            if (name.contentEquals(e.enclosingElement.simpleName)) {
                ElementHelper.logger.error("一个普通方法 “$name”不应当与类名重复，避免与构造函数产生混淆", e)
            }
            checkCamelCase(e, false)
        }
        return super.visitExecutable(e, p)
    }
}