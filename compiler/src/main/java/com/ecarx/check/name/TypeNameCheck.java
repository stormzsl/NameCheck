package com.ecarx.check.name;

import com.ecarx.check.ValidatorUtils;

import javax.lang.model.element.TypeElement;

/*
 * 类/接口命名检查器
 * 符合驼峰命名规则：首字母需要大写
 */
public class TypeNameCheck extends BaseNameCheck {

    @Override
    public Void visitType(TypeElement e, Void p) {
        ValidatorUtils.checkCamelCase(e, true);
        return super.visitType(e, p);
    }
}
