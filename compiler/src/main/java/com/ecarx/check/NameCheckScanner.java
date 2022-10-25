package com.ecarx.check;

import java.util.EnumSet;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner6;
import static javax.lang.model.element.ElementKind.ENUM_CONSTANT;
import static javax.lang.model.element.ElementKind.FIELD;
import static javax.lang.model.element.ElementKind.INTERFACE;
import static javax.lang.model.element.ElementKind.METHOD;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/*
 * 命名规范检查具体实现类，以Visitor的方式访问AST语法树中的元素
 */
public class NameCheckScanner extends ElementScanner6<Void,Void> {

    private final Logger logger;

    private final ElementHelper elementHelper;

    public NameCheckScanner(ProcessingEnvironment processingEnvironment){
        this.logger = new Logger(processingEnvironment.getMessager());
        this.elementHelper = new ElementHelper(processingEnvironment);
    }
    /**
     * 此方法用于检查Java类/接口
     */
    @Override
    public Void visitType(TypeElement e, Void p) {
        scan(e.getTypeParameters(), p);
        checkCamelCase(e, true);
        super.visitType(e, p);
        return null;
    }

    /**
     * 检查方法命名是否合法
     * getEnclosingElement():获取该元素的父元素
     */
    @Override
    public Void visitExecutable(ExecutableElement e, Void p) {
        if (e.getKind() == METHOD) {
            Name name = e.getSimpleName();
            if (name.contentEquals(e.getEnclosingElement().getSimpleName())){
                logger.error("一个普通方法 “" + name + "”不应当与类名重复，避免与构造函数产生混淆", e);
            }
            checkCamelCase(e, false);
        }
        super.visitExecutable(e, p);
        return null;
    }

    /**
     * 检查变量命名是否合法
     */
    @Override
    public Void visitVariable(VariableElement e, Void p) {
        // 2.如果这个Variable是枚举或常量，则按大写命名检查，否则按照驼式命名法规则检查
        if (e.getKind() == ENUM_CONSTANT || e.getConstantValue() != null || variableConstant(e)){
            checkAllCaps(e);
        }
        else{
            checkCamelCase(e, false);
        }
        return null;
    }

    /**
     * 判断一个变量是否是常量
     */
    private boolean variableConstant(VariableElement e) {
        if (e.getEnclosingElement().getKind() == INTERFACE){
            return true;
        }
        else return e.getKind() == FIELD && e.getModifiers().containsAll(EnumSet.of(PUBLIC, STATIC, FINAL));
    }

    /**
     * 检查传入的Element是否符合驼式命名法，如果不符合，则输出警告信息
     */
    private void checkCamelCase(Element e, boolean initialCaps) {

        //1.如果是系统类包括的过滤
        if(elementHelper.matchSystemElement(e)){
            return;
        }

        String name = e.getSimpleName().toString();
        boolean previousUpper = false;
        boolean conventional = true;
        int firstCodePoint = name.codePointAt(0);

        if (Character.isUpperCase(firstCodePoint)) {
            previousUpper = true;
            if (!initialCaps) {
                logger.error( "名称“" + name + "”应当以小写字母开头",e);
                return;
            }
        } else if (Character.isLowerCase(firstCodePoint)) {
            if (initialCaps) {
               logger.error("名称“" + name + "”应当以大写字母开头",e);
                return;
            }
        } else{
            conventional = false;
        }

        if (conventional) {
            int cp = firstCodePoint;
            for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                cp = name.codePointAt(i);
                if (Character.isUpperCase(cp)) {
                    if (previousUpper) {
                        conventional = false;
                        break;
                    }
                    previousUpper = true;
                } else
                    previousUpper = false;
            }
        }

        if (!conventional)
//            messager.printMessage(Diagnostic.Kind.ERROR, "名称“" + name + "”应当符合驼式命名法（Camel Case Names）", e);
            logger.error("名称“" + name + "”应当符合驼式命名法（Camel Case Names）",e);
    }

    /**
     * 大写命名检查，要求第一个字母必须是大写的英文字母，其余部分可以是下划线或大写字母
     */
    private void checkAllCaps(Element e) {

        //1.如果是系统类包括的过滤
        if(elementHelper.matchSystemElement(e)){
            return;
        }

        String name = e.getSimpleName().toString();

        boolean conventional = true;
        int firstCodePoint = name.codePointAt(0);

        if (!Character.isUpperCase(firstCodePoint))
            conventional = false;
        else {
            boolean previousUnderscore = false;
            int cp = firstCodePoint;
            for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                cp = name.codePointAt(i);
                if (cp == (int) '_') {
                    if (previousUnderscore) {
                        conventional = false;
                        break;
                    }
                    previousUnderscore = true;
                } else {
                    previousUnderscore = false;
                    if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                        conventional = false;
                        break;
                    }
                }
            }
        }

        if (!conventional)
            logger.error( "常量“" + name + "”应当全部以大写字母或下划线命名，并且以字母开头",e);
    }

}