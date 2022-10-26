package com.ecarx.check;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static javax.lang.model.element.ElementKind.FIELD;
import static javax.lang.model.element.ElementKind.INTERFACE;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

public final class ValidatorUtils {

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 检查传入的Element是否符合驼式命名法，如果不符合，则抛出error信息中断编译
     */
    public static void checkCamelCase(Element e, boolean initialCaps) {

        //1.如果是系统类包括的过滤
        if(ElementHelper.getInstance().matchSystemElement(e)){
            return;
        }

        String name = e.getSimpleName().toString();
        boolean previousUpper = false;
        boolean conventional = true;
        int firstCodePoint = name.codePointAt(0);

        if (Character.isUpperCase(firstCodePoint)) {
            previousUpper = true;
            if (!initialCaps) {
                ElementHelper.getInstance().getLogger().error( "名称“" + name + "”应当以小写字母开头",e);
                return;
            }
        } else if (Character.isLowerCase(firstCodePoint)) {
            if (initialCaps) {
                ElementHelper.getInstance().getLogger().error("名称“" + name + "”应当以大写字母开头",e);
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
            ElementHelper.getInstance().getLogger().error("名称“" + name + "”应当符合驼式命名法（Camel Case Names）",e);
    }

    /**
     * 大写命名检查，要求第一个字母必须是大写的英文字母，其余部分可以是下划线或大写字母
     */
    public static void checkAllCaps(Element e) {

        //1.如果是系统类包括的过滤
        if(ElementHelper.getInstance().matchSystemElement(e)){
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
            ElementHelper.getInstance().getLogger().error( "常量“" + name + "”应当全部以大写字母或下划线命名，并且以字母开头",e);
    }

    /**
     * 判断一个变量是否是常量
     */
    public static boolean variableConstant(VariableElement e) {
        if (e.getEnclosingElement().getKind() == INTERFACE){
            return true;
        }
        else return e.getKind() == FIELD && e.getModifiers().containsAll(EnumSet.of(PUBLIC, STATIC, FINAL));
    }


}
