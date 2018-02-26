package com.mindorks.placeholderview.processor;

import com.mindorks.placeholderview.annotations.Layout;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by ali on 26/02/18.
 */

public class Validator {

    private Messager messager;

    public Validator(Messager messager) {
        this.messager = messager;
    }

    public boolean validateLayout(TypeElement typeElement) {
        Layout layout = typeElement.getAnnotation(Layout.class);
        if (layout.value() > 0) {
            return true;
        } else {
            messager.printMessage(Diagnostic.Kind.ERROR,
                    "@Layout should have positive value passed");
            return false;
        }
    }

    public boolean validateTypeElement(Element element) {
        if (element.getKind() == ElementKind.CLASS) {
            return true;
        } else {
            messager.printMessage(Diagnostic.Kind.ERROR, "Can be applied to class.");
            return false;
        }
    }
}
