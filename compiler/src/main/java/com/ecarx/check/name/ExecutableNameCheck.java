package com.ecarx.check.name;

import com.ecarx.check.ElementHelper;
import com.ecarx.check.ValidatorUtils;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;

import static javax.lang.model.element.ElementKind.METHOD;

/* 方法命名检查器
 * 检查所有方法包括构造方法，命名规则是否符合驼峰规范，首字母小写
 */
public class ExecutableNameCheck extends BaseNameCheck {
    @Override
   public Void visitExecutable(ExecutableElement e, Void p) {
        if (e.getKind() == METHOD) {
            Name name = e.getSimpleName();
            if (name.contentEquals(e.getEnclosingElement().getSimpleName())){
                ElementHelper.getInstance().getLogger().error("一个普通方法 “" + name + "”不应当与类名重复，避免与构造函数产生混淆", e);
            }
            ValidatorUtils.checkCamelCase(e, false);
        }
        return super.visitExecutable(e, p);
    }
}
