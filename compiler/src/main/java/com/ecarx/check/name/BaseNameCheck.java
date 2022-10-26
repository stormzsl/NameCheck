package com.ecarx.check.name;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/*
 * 命名检查器基类,便于后续功能拓展
 */
public class BaseNameCheck {

   public Void visitType(TypeElement e, Void p){
        return null;
    }

    public Void visitExecutable(ExecutableElement e, Void p){
        return null;
    }

    public Void visitVariable(VariableElement e, Void p){
        return null;
    }

}
