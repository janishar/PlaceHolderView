package com.mindorks.placeholderview.processor;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Toggle;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;

/**
 * Created by ali on 26/02/18.
 */

public class Validator {

    private Validator() {
        // Not to be instantiated
    }

    public static void validateNonPrivateModifier(Element element) throws IllegalUseException {
        if (element.getModifiers().contains(Modifier.PRIVATE)) {
            String msg = "This library only support annotations on public, protected or local, use with private is not supported";
            throw new IllegalUseException(msg);
        }
    }

    public static TypeElement validateLayout(TypeElement typeElement) throws IllegalUseException {
        validateNonPrivateModifier(typeElement);
        Layout layout = typeElement.getAnnotation(Layout.class);
        if (layout.value() <= 0) {
            String msg = "@Layout should have positive value passed";
            throw new IllegalUseException(msg);
        }
        return typeElement;
    }

    public static Element validateTypeElement(Element element) throws IllegalUseException {
        validateNonPrivateModifier(element);
        if (element.getKind() != ElementKind.CLASS) {
            String msg = "@Layout can only be applied to a class.";
            throw new IllegalUseException(msg);
        }
        return element;
    }

    public static void validateView(Element element, View view) throws IllegalUseException {
        validateNonPrivateModifier(element);
        if (view != null && view.value() <= 0) {
            String msg = "@View should have positive value passed";
            throw new IllegalUseException(msg);
        }
    }

    public static void validateClick(Element element, Click click) throws IllegalUseException {
        validateNonPrivateModifier(element);
        if (click != null && click.value() <= 0) {
            String msg = "@Click should have positive value passed";
            throw new IllegalUseException(msg);
        }
    }

    public static void validateLongClick(Element element, LongClick longClick) throws IllegalUseException {
        validateNonPrivateModifier(element);
        if (longClick != null && longClick.value() <= 0) {
            String msg = "@LongClick should have positive value passed";
            throw new IllegalUseException(msg);
        }
    }

    public static void validateResolve(ExecutableElement element) throws IllegalUseException {
        validateNonPrivateModifier(element);
        if (element.getParameters().size() > 0) {
            String msg = "@Resolve should have no parameters";
            throw new IllegalUseException(msg);
        }
    }

    public static void validateRecycle(ExecutableElement element) throws IllegalUseException {
        validateNonPrivateModifier(element);
        if (element.getParameters().size() > 0) {
            String msg = "@Recycle should have no parameters";
            throw new IllegalUseException(msg);
        }
    }

    public static void validatePosition(VariableElement element) throws IllegalUseException {
        validateNonPrivateModifier(element);
        if (element.asType().getKind() != TypeKind.INT) {
            String msg = "@Position should only be used for int";
            throw new IllegalUseException(msg);
        }
    }

    public static void validateToggle(Element element, Toggle toggle) throws IllegalUseException {
        validateNonPrivateModifier(element);
        if (toggle != null && toggle.value() <= 0) {
            String msg = "@Toggle should have positive value passed";
            throw new IllegalUseException(msg);
        }
    }

    public static void validateExpand(ExecutableElement element) throws IllegalUseException {
        validateNonPrivateModifier(element);
        if (element.getParameters().size() > 0) {
            String msg = "@Expand should have no parameters";
            throw new IllegalUseException(msg);
        }
    }

    public static void validateCollapse(ExecutableElement element) throws IllegalUseException {
        validateNonPrivateModifier(element);
        if (element.getParameters().size() > 0) {
            String msg = "@Collapse should have no parameters";
            throw new IllegalUseException(msg);
        }
    }

    public static void validateSwipeView(VariableElement element) throws IllegalUseException {
        validateNonPrivateModifier(element);
        if (!element.asType().toString().equals("android.view.View")) {
            String msg = "@SwipeView should be used only on android.view.View";
            throw new IllegalUseException(msg);
        }
    }
}
