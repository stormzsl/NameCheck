package com.ecarx.check

//import com.google.auto.service.AutoService
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@SupportedAnnotationTypes("*") //用"*"表示支持所有的Annotations
//@AutoService(Processor::class)
class CheckProcessor : AbstractProcessor() {
    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    /**
     * 跟元素相关的辅助类，帮助我们去获取一些元素相关的信息
     * - VariableElement  代表一个 字段, 枚举常量, 方法或者构造方法的参数, 局部变量及 异常参数等元素
     * - ExecutableElement 代表代码方法，构造函数，类或接口的初始化代码块等元素，也包括注解类型元素
     * - TypeElement  代表类或接口元素
     * - PackageElement  代表包元素
     */
    private var checkDispatcher: CheckDispatcher? = null
    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        checkDispatcher = CheckDispatcher(processingEnv)
    }

    /*
     * 对输入的语法树各个节点进行命名名称规范检查
     */
    override fun process(set: Set<TypeElement?>, roundEnvironment: RoundEnvironment): Boolean {
        if (!roundEnvironment.processingOver()) {
            for (element in roundEnvironment.rootElements) {
                checkDispatcher!!.checkNames(element)
            }
        }
        return false
    }
}