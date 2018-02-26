package com.mindorks.placeholderview.processor;

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
    private ClassName binderClassName;
    private ClassName viewBinderClassName;
    private ClassName androidViewClassName;
    private ClassName onClickListenerClassName;
    private List<VariableElement> variableElements;
    private List<ExecutableElement> executableElements;

    private ClassDetail() {
    }

    public static ClassDetail build(TypeElement typeElement, String packageName, String classNameSuffix) {
        ClassDetail detail = new ClassDetail();
        detail.typeElement = typeElement;
        detail.packageName = packageName;
        detail.typeName = typeElement.getSimpleName().toString();
        detail.className = ClassName.get(packageName, detail.typeName);
        detail.binderClassName = ClassName.get(packageName,
                detail.typeName + classNameSuffix);
        detail.viewBinderClassName = ClassName.get(NameStore.Package.PLACE_HOLDER_VIEW, NameStore.Class.VIEW_BINDER);
        detail.androidViewClassName = ClassName.get(NameStore.Package.ANDROID_VIEW, NameStore.Class.ANDROID_VIEW);
        detail.variableElements = ElementFilter.fieldsIn(typeElement.getEnclosedElements());
        detail.executableElements = ElementFilter.methodsIn(typeElement.getEnclosedElements());
        detail.onClickListenerClassName = ClassName.get(
                NameStore.Package.ANDROID_VIEW,
                NameStore.Class.ANDROID_VIEW,
                NameStore.Class.ANDROID_VIEW_ON_CLICK_LISTENER);
        return detail;
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

    public ClassName getBinderClassName() {
        return binderClassName;
    }

    public ClassName getViewBinderClassName() {
        return viewBinderClassName;
    }

    public ClassName getAndroidViewClassName() {
        return androidViewClassName;
    }

    public List<VariableElement> getVariableElements() {
        return variableElements;
    }

    public List<ExecutableElement> getExecutableElements() {
        return executableElements;
    }

    public ClassName getOnClickListenerClassName() {
        return onClickListenerClassName;
    }
}
