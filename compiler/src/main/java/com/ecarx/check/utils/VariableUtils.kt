package com.ecarx.check.utils

import java.util.*
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement

/*
 * getEnclosingElement() 获取该元素的父元素,如果是PackageElement则返回null，
 * 如果是TypeElement则返回PackageElement，如果是TypeParameterElement则返回泛型Element
 *
 * getEnclosedElements() 获取该元素上的直接子元素，类似一个类中有VariableElement。
 */

/**
 * 判断一个变量是否是常量
 * getConstantValue() :如果属性变量被final修饰，则可以使用该方法获取它的值
 */
fun variableIsConstant(variableElement: VariableElement): Boolean {
    return if (variableElement.enclosingElement.kind == ElementKind.INTERFACE) {
        true
    } else if (variableElement.constantValue != null) {
        true
    } else variableElement.kind == ElementKind.FIELD && variableElement.modifiers.containsAll(
        EnumSet.of(
            Modifier.PUBLIC,
            Modifier.STATIC,
            Modifier.FINAL
        )
    )
}

/*
 * 判断变量是否是枚举常量
 */
fun variableIsEnum(variableElement: VariableElement): Boolean =
    variableElement.kind == ElementKind.ENUM_CONSTANT

fun isValidFieldElement(element: Element): Boolean {
    return element.kind.isField
}


