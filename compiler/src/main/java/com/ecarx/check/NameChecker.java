package com.ecarx.check;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/*
 * 命名规范检查器
 * 如果不满足命名规范会中断编译过程并输出结果
 */
public class NameChecker {

    private NameCheckScanner nameCheckScanner;


    public NameChecker(ProcessingEnvironment processingEnv) {
        this.nameCheckScanner = new NameCheckScanner(processingEnv);
    }

    /*
     * 对代码中的类，接口，字段和方法进行检查
     * 类/接口:符合驼峰命名法，首字母大写
     * 字段：驼峰命名法，首字母小写
     * 方法:符合驼峰命名法，首字母小写
     * 常量:要求全部大写
     */
    public void checkNames(Element element) {
        nameCheckScanner.scan(element);
    }
}
