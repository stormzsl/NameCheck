package com.ecarx.check

import javax.annotation.processing.ProcessingEnvironment
import com.ecarx.check.CheckScanner
import com.ecarx.check.ElementHelper
import javax.lang.model.element.Element

/*
 * 命名规范检查器：如果不满足命名规范会中断编译过程并输出结果
 * 对代码中的类，接口，字段和方法进行检查
 * 类/接口:符合驼峰命名法，首字母大写
 * 字段/参数：驼峰命名法，首字母小写
 * 方法:符合驼峰命名法，首字母小写
 * 常量:要求全部大写
 */
class CheckDispatcher(processingEnv: ProcessingEnvironment) {
    private var _checkScanner: CheckScanner = CheckScanner()

    init {
        init(processingEnv)
    }

    private fun init(processingEnv: ProcessingEnvironment) {
        ElementHelper.init(processingEnv)
    }

    fun checkNames(element: Element?) {
        _checkScanner.scan(element)
    }


}