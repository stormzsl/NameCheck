package com.ecarx.check;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;


@SupportedAnnotationTypes("*") //用"*"表示支持所有的Annotations
@AutoService(Processor.class)
public class CheckProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 跟元素相关的辅助类，帮助我们去获取一些元素相关的信息
     * - VariableElement  代表一个 字段, 枚举常量, 方法或者构造方法的参数, 局部变量及 异常参数等元素
     * - ExecutableElement 代表代码方法，构造函数，类或接口的初始化代码块等元素，也包括注解类型元素
     * - TypeElement  代表类或接口元素
     * - PackageElement  代表包元素
     */
    private NameChecker nameChecker;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        nameChecker = new NameChecker(processingEnv);
    }

    /*
     * 对输入的语法树各个节点进行命名名称规范检查
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if(!roundEnvironment.processingOver()){
            for (Element element : roundEnvironment.getRootElements()) {
                nameChecker.checkNames(element);
            }
        }
        return false;
    }
}
