package com.mindorks.placeholderview.compiler.core;

import com.squareup.javapoet.ClassName;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;

/**
 * Created by janisharali on 26/02/18.
 */

public class ClassDetail {
    private TypeElement typeElement;
    private String packageName;
    private String typeName;
    private ClassName className;
    private ClassName generatedClassName;
    private ClassName superClassName;
    private ClassName androidViewClassName;
    private ClassName androidOnClickListenerClassName;
    private ClassName androidOnLongClickListenerClassName;
    private ClassName frameViewClassName;
    private ClassName swipeOptionClassName;
    private ClassName swipeDirectionalOptionClassName;
    private ClassName swipeDecorClassName;
    private ClassName viewTypeParameterClassName;
    private ClassName swipeDirectionClassName;
    private List<VariableElement> variableElements;
    private List<ExecutableElement> executableElements;

    public ClassDetail(TypeElement typeElement, String packageName,
                       String baseClassName, String generatedClassNameSuffix) {
        this.typeElement = typeElement;
        this.packageName = packageName;
        typeName = typeElement.getSimpleName().toString();

        className = ClassName.get(packageName, typeName);

        generatedClassName = ClassName.get(
                packageName,
                typeName + generatedClassNameSuffix);

        superClassName = ClassName.get(
                NameStore.Package.PLACE_HOLDER_VIEW,
                baseClassName);

        androidViewClassName = ClassName.get(
                NameStore.Package.ANDROID_VIEW,
                NameStore.Class.ANDROID_VIEW);

        androidOnClickListenerClassName = ClassName.get(
                NameStore.Package.ANDROID_VIEW,
                NameStore.Class.ANDROID_VIEW,
                NameStore.Class.ANDROID_VIEW_ON_CLICK_LISTENER);

        androidOnLongClickListenerClassName = ClassName.get(
                NameStore.Package.ANDROID_VIEW,
                NameStore.Class.ANDROID_VIEW,
                NameStore.Class.ANDROID_VIEW_ON_LONG_CLICK_LISTENER);

        frameViewClassName = ClassName.get(
                NameStore.Package.PLACE_HOLDER_VIEW,
                NameStore.Class.SWIPE_PLACE_HOLDER_VIEW,
                NameStore.Class.FRAME_VIEW);

        swipeOptionClassName = ClassName.get(
                NameStore.Package.PLACE_HOLDER_VIEW,
                NameStore.Class.SWIPE_PLACE_HOLDER_VIEW,
                NameStore.Class.SWIPE_OPTION);

        swipeDirectionalOptionClassName = ClassName.get(
                NameStore.Package.PLACE_HOLDER_VIEW,
                NameStore.Class.SWIPE_DIRECTIONAL_VIEW,
                NameStore.Class.SWIPE_DIRECTIONAL_OPTION);

        swipeDecorClassName = ClassName.get(
                NameStore.Package.PLACE_HOLDER_VIEW,
                NameStore.Class.SWIPE_DECOR);

        swipeDirectionClassName = ClassName.get(
                NameStore.Package.PLACE_HOLDER_VIEW,
                NameStore.Class.SWIPE_DIRECTION);

        viewTypeParameterClassName = androidViewClassName;

        variableElements = ElementFilter.fieldsIn(typeElement.getEnclosedElements());
        executableElements = ElementFilter.methodsIn(typeElement.getEnclosedElements());
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getTypeName() {
        return typeName;
    }

    public ClassName getClassName() {
        return className;
    }

    public ClassName getGeneratedClassName() {
        return generatedClassName;
    }

    public ClassName getSuperClassName() {
        return superClassName;
    }

    public ClassName getAndroidViewClassName() {
        return androidViewClassName;
    }

    public ClassName getAndroidOnClickListenerClassName() {
        return androidOnClickListenerClassName;
    }

    public ClassName getAndroidOnLongClickListenerClassName() {
        return androidOnLongClickListenerClassName;
    }

    public ClassName getFrameViewClassName() {
        return frameViewClassName;
    }

    public ClassName getSwipeOptionClassName() {
        return swipeOptionClassName;
    }

    public ClassName getSwipeDirectionalOptionClassName() {
        return swipeDirectionalOptionClassName;
    }

    public ClassName getSwipeDecorClassName() {
        return swipeDecorClassName;
    }

    public ClassName getViewTypeParameterClassName() {
        return viewTypeParameterClassName;
    }

    public void changeViewTypeParameterClassName(ClassName viewTypeParameterClassName) {
        this.viewTypeParameterClassName = viewTypeParameterClassName;
    }

    public ClassName getSwipeDirectionClassName() {
        return swipeDirectionClassName;
    }

    public List<VariableElement> getVariableElements() {
        return variableElements;
    }

    public List<ExecutableElement> getExecutableElements() {
        return executableElements;
    }
}
