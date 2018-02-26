package com.mindorks.placeholderview.processor;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.View;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Created by ali on 26/02/18.
 */

public class Validator {

    private Validator() {
        // Not to be instantiated
    }

    public static TypeElement validateLayout(TypeElement typeElement) throws InvalidUseException {
        Layout layout = typeElement.getAnnotation(Layout.class);
        if (layout.value() <= 0) {
            String msg = "@Layout should have positive value passed";
            throw new InvalidUseException(msg);
        }
        return typeElement;
    }

    public static Element validateTypeElement(Element element) throws InvalidUseException {
        if (element.getKind() != ElementKind.CLASS) {
            String msg = "@Layout can only be applied to a class.";
            throw new InvalidUseException(msg);
        }
        return element;
    }

    public static void validateView(View view) throws InvalidUseException {
        if (view != null && view.value() <= 0) {
            String msg = "@View should have positive value passed";
            throw new InvalidUseException(msg);
        }
    }

    public static void validateClick(Click click) throws InvalidUseException {
        if (click != null && click.value() <= 0) {
            String msg = "@Click should have positive value passed";
            throw new InvalidUseException(msg);
        }
    }
}
