package com.ecarx.check

import com.ecarx.check.utils.matchSystemElement
import javax.lang.model.element.Element

const val ANDROID_APP_ACTIVITY_JAVA = "android.app.activity"
const val ANDROID_GENERATE_R_JAVA = "R."
const val ANDROIDX_PACKAGE = "androidx."
const val JAVA_PACKAGE = "java."
const val ANDROID_PACKAGE = "android."


fun isEmpty(cs: CharSequence?): Boolean {
    return cs == null || cs.isEmpty()
}

fun isEmpty(coll: Collection<*>?): Boolean {
    return coll == null || coll.isEmpty()
}

fun isEmpty(map: Map<*, *>?): Boolean {
    return map == null || map.isEmpty()
}

/**
 * 检查传入的Element是否符合驼式命名法，如果不符合，则抛出error信息中断编译
 */
fun checkCamelCase(e: Element, initialCaps: Boolean) {

    //1.如果是系统类包括的过滤
    if (matchSystemElement(e)) {
        return
    }
    val name = e.simpleName.toString()
    var previousUpper = false
    var conventional = true
    val firstCodePoint = name.codePointAt(0)
    if (Character.isUpperCase(firstCodePoint)) {
        previousUpper = true
        if (!initialCaps) {
            ElementHelper.logger.error("名称“$name”应当以小写字母开头", e)
            return
        }
    } else if (Character.isLowerCase(firstCodePoint)) {
        if (initialCaps) {
            ElementHelper.logger.error("名称“$name”应当以大写字母开头", e)
            return
        }
    } else {
        conventional = false
    }
    if (conventional) {
        var cp = firstCodePoint
        var i = Character.charCount(cp)
        while (i < name.length) {
            cp = name.codePointAt(i)
            if (Character.isUpperCase(cp)) {
                if (previousUpper) {
                    conventional = false
                    break
                }
                previousUpper = true
            } else previousUpper = false
            i += Character.charCount(cp)
        }
    }
    if (!conventional) //            messager.printMessage(Diagnostic.Kind.ERROR, "名称“" + name + "”应当符合驼式命名法（Camel Case Names）", e);
        ElementHelper.logger.error("名称应当符合驼式命名法", e)
}

/**
 * 大写命名检查，要求第一个字母必须是大写的英文字母，其余部分可以是下划线或大写字母
 */
fun checkAllCaps(e: Element) {

    //1.如果是系统类包括的过滤
    if (matchSystemElement(e)) {
        return
    }
    val name = e.simpleName.toString()
    var conventional = true
    val firstCodePoint = name.codePointAt(0)
    if (!Character.isUpperCase(firstCodePoint)) conventional = false else {
        var previousUnderscore = false
        var cp = firstCodePoint
        var i = Character.charCount(cp)
        while (i < name.length) {
            cp = name.codePointAt(i)
            if (cp == '_'.toInt()) {
                if (previousUnderscore) {
                    conventional = false
                    break
                }
                previousUnderscore = true
            } else {
                previousUnderscore = false
                if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                    conventional = false
                    break
                }
            }
            i += Character.charCount(cp)
        }
    }
    if (!conventional) ElementHelper.logger.error(
        "常量应当全部以大写字母或下划线命名，并且以字母开头", e)
}