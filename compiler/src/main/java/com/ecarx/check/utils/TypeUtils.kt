package com.ecarx.check.utils

import com.ecarx.check.*
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement


//获取节点所属包名
fun getPackageName(element: TypeElement?): String {
    return ElementHelper.elementsUtils.getPackageOf(element).qualifiedName.toString()
}

// 获取类名
fun getSimpleClassName(element: TypeElement): String {
    return element.simpleName.toString()
}

// 获取包名+类名
fun getFullClassName(element: TypeElement): String {
    return element.qualifiedName.toString()
}

fun isValidTypeElement(element: TypeElement): Boolean {
    return element.kind.isClass
}

/**
 * 判断修饰符是不是PRIVATE
 */
fun isPrivate(annotatedClass: Element): Boolean {
    return annotatedClass.modifiers.contains(Modifier.PRIVATE)
}

//fun matchSystemElement(element: Element?): Boolean {
//    if (element == null) {
//        return false
//    }
//    if (element is TypeElement) {
//        return matchSystemTypeElement(element)
//    }
//    return if (element.enclosingElement is TypeElement) {
//        matchSystemTypeElement(element.enclosingElement as TypeElement)
//    } else false
//}

//// 判断是否是系统类如R.java，android.*
//fun matchSystemTypeElement(element: TypeElement): Boolean {
//    if (getFullClassName(element).contains(ANDROID_GENERATE_R_JAVA)) {
//        return true
//    }
//    if (getFullClassName(element).startsWith(ANDROIDX_PACKAGE)) {
//        return true
//    }
//    if (getFullClassName(element).startsWith(ANDROID_PACKAGE)) {
//        return true
//    }
//    return getFullClassName(element).startsWith(JAVA_PACKAGE)
//}

/**
 * if true mean this class is java class
 */
private fun isJavaFile(element: TypeElement): Boolean {
    val tmMetadata = ElementHelper.elementsUtils.getTypeElement("kotlin.Metadata").asType()
    return element.annotationMirrors.find { it.annotationType == tmMetadata } == null
}

fun packageNameOf(element: Element): String? {
    var type = element
    while (true) {
        val enclosing = type.enclosingElement ?: throw RuntimeException("null value from type.getEnclosingElement()")
        if (enclosing is PackageElement) {
            return enclosing.qualifiedName.toString()
        }
        type = enclosing
    }
}
