package com.ecarx.check;

import com.ecarx.check.name.ExecutableNameCheck;
import com.ecarx.check.name.TypeNameCheck;
import com.ecarx.check.name.VariableNameCheck;

import java.util.EnumSet;

import javax.annotation.processing.ProcessingEnvironment;
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
public class CheckScanner extends ElementScanner6<Void,Void> {

    /*
     * ast访问类/接口节点触发
     */
    @Override
    public Void visitType(TypeElement e, Void p) {
        scan(e.getTypeParameters(), p);
        new TypeNameCheck().visitType(e,p);
        super.visitType(e, p);
        return null;
    }

    /**
     * ast访问方法节点触发
     */
    @Override
    public Void visitExecutable(ExecutableElement e, Void p) {
        new ExecutableNameCheck().visitExecutable(e,p);
        super.visitExecutable(e, p);
        return null;
    }

    /**
     * ast访问成员变量，方法参数节点触发
     */
    @Override
    public Void visitVariable(VariableElement e, Void p) {
        new VariableNameCheck().visitVariable(e,p);

        return null;
    }
}
