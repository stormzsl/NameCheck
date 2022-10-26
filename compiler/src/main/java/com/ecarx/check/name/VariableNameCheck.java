package com.ecarx.check.name;

import com.ecarx.check.ValidatorUtils;

import javax.lang.model.element.VariableElement;

import static javax.lang.model.element.ElementKind.ENUM_CONSTANT;

public class VariableNameCheck extends BaseNameCheck{

    @Override
    public Void visitVariable(VariableElement e, Void p) {
        //如果这个Variable是枚举或常量，则按大写命名检查，否则按照驼式命名法规则检查
        if (e.getKind() == ENUM_CONSTANT || e.getConstantValue() != null || ValidatorUtils.variableConstant(e)){
            ValidatorUtils.checkAllCaps(e);
        }
        else{
            ValidatorUtils.checkCamelCase(e, false);
        }
        return super.visitVariable(e, p);
    }
}
