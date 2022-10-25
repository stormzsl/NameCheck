package com.ecarx.check;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class Logger {

    private final Messager messager;

    public Logger(Messager messager) {
        this.messager = messager;
    }

    private void printMessage(Diagnostic.Kind kindType, String msg,Element element) {
        if (msg == null || element == null) {
            return;
        }
        messager.printMessage(kindType, msg,element);
    }

    public void info(String msg,Element element) {
        printMessage(Diagnostic.Kind.NOTE, msg,element);
    }

    public void error(String msg,Element element) {
        printMessage(Diagnostic.Kind.ERROR, msg,element);
    }

    public void warn(String msg,Element element) {
        printMessage(Diagnostic.Kind.WARNING, msg,element);
    }
}
