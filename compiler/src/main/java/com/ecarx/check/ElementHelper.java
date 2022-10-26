package com.ecarx.check;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.ecarx.check.Constants.ANDROIDX_PACKAGE;
import static com.ecarx.check.Constants.ANDROID_APP_ACTIVITY_JAVA;
import static com.ecarx.check.Constants.ANDROID_GENERATE_R_JAVA;
import static com.ecarx.check.Constants.ANDROID_PACKAGE;
import static com.ecarx.check.Constants.JAVA_PACKAGE;
import static javax.lang.model.element.Modifier.PRIVATE;

public class ElementHelper {

    private Elements elementsUtils;
    private Types typeUtils;
    private Logger logger;

    private static class ElementHelperSingleHolder{

        private static final ElementHelper INSTANCE = new ElementHelper();
    }

    public static ElementHelper getInstance(){
        return ElementHelperSingleHolder.INSTANCE;
    }

    public void init(ProcessingEnvironment environment){
        this.elementsUtils = environment.getElementUtils();
        this.typeUtils = environment.getTypeUtils();
        this.logger = new Logger(environment.getMessager());
    }

    public Elements getElementsUtils() {
        return elementsUtils;
    }

    public Types getTypeUtils() {
        return typeUtils;
    }

    public Logger getLogger() {
        return logger;
    }

    //获取节点所属包名
    public String getPackageName(TypeElement element){
        return elementsUtils.getPackageOf(element).getQualifiedName().toString();
    }

    // 获取类名
    public String getSimpleClassName(TypeElement element){
        return element.getSimpleName().toString();
    }

    // 获取包名+类名
   public String getFullClassName(TypeElement element){
      return element.getQualifiedName().toString() ;
   }

    /**
     * 判断修饰符是不是PRIVATE
     */
    public static boolean isPrivate(Element annotatedClass) {
        return annotatedClass.getModifiers().contains(PRIVATE);
    }


    public boolean isValidTypeElement(TypeElement element){
        return element.getKind().isClass();
    }

    public boolean  isValidFieldElement(Element element){
        return element.getKind().isField();
    }

    //是否是Activity的子类
    public boolean isSubActivity(TypeElement element){
         TypeElement activityElement =elementsUtils.getTypeElement(ANDROID_APP_ACTIVITY_JAVA);
        return typeUtils.isSubtype(element.asType(),activityElement.asType());
    }

    public boolean matchSystemElement(Element element){
        if(element == null){
            return false;
        }

        if(element instanceof TypeElement){
            return matchSystemTypeElement((TypeElement) element);
        }

        if(element.getEnclosingElement() instanceof TypeElement){
            return matchSystemTypeElement((TypeElement) element.getEnclosingElement());
        }
        return false;
    }
    // 判断是否是系统类如R.java，android.*
    public boolean matchSystemTypeElement(TypeElement element){
        if(getFullClassName(element).contains(ANDROID_GENERATE_R_JAVA)){
            return true;
        }

        if (getFullClassName(element).startsWith(ANDROIDX_PACKAGE)){
            return true;
        }

        if (getFullClassName(element).startsWith(ANDROID_PACKAGE)){
            return true;
        }

        if (getFullClassName(element).startsWith(JAVA_PACKAGE)){
            return true;
        }

        return  false;
    }

}
