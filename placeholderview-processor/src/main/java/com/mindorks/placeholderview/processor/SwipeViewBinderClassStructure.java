package com.mindorks.placeholderview.processor;

import com.mindorks.placeholderview.annotations.internal.BindingSuffix;
import com.mindorks.placeholderview.annotations.swipe.SwipeView;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by janisharali on 28/02/18.
 */

public class SwipeViewBinderClassStructure extends ViewBinderClassStructure {

    public SwipeViewBinderClassStructure(ClassDetail classDetail) {
        super(classDetail);
    }

    public static SwipeViewBinderClassStructure create(TypeElement typeElement, Elements elementUtils) {
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        ClassDetail classDetail = new ClassDetail(
                typeElement,
                packageName,
                NameStore.Class.SWIPE_VIEW_BINDER,
                BindingSuffix.CLASS_SWIPE_VIEW_BINDER_SUFFIX);
        classDetail.changeViewTypeParameterClassName(classDetail.getFrameViewClassName());
        return new SwipeViewBinderClassStructure(classDetail);
    }

    @Override
    protected TypeSpec.Builder createClassBuilder() {
        return TypeSpec.classBuilder(getClassDetail().getGeneratedClassName())
                .superclass(ParameterizedTypeName.get(
                        getClassDetail().getSuperClassName(),
                        TypeVariableName.get(getClassDetail().getTypeElement().asType()),
                        getClassDetail().getFrameViewClassName(),
                        getClassDetail().getSwipeOptionClassName(),
                        getClassDetail().getSwipeDecorClassName()))
                .addModifiers(Modifier.PUBLIC);
    }

    @Override
    public SwipeViewBinderClassStructure addConstructor() {
        return (SwipeViewBinderClassStructure) super.addConstructor();
    }

    @Override
    public SwipeViewBinderClassStructure addResolveViewMethod() throws IllegalUseException {
        return (SwipeViewBinderClassStructure) super.addResolveViewMethod();
    }

    @Override
    public SwipeViewBinderClassStructure addRecycleViewMethod() throws IllegalUseException {
        return (SwipeViewBinderClassStructure) super.addRecycleViewMethod();
    }

    @Override
    public SwipeViewBinderClassStructure addBindViewPositionMethod() throws IllegalUseException {
        return (SwipeViewBinderClassStructure) super.addBindViewPositionMethod();
    }

    @Override
    public SwipeViewBinderClassStructure addBindViewMethod() throws IllegalUseException {
        return (SwipeViewBinderClassStructure) super.addBindViewMethod();
    }

    @Override
    public SwipeViewBinderClassStructure addBindClickMethod() throws IllegalUseException {
        return (SwipeViewBinderClassStructure) super.addBindClickMethod();
    }

    @Override
    public SwipeViewBinderClassStructure addBindLongClickMethod() throws IllegalUseException {
        return (SwipeViewBinderClassStructure) super.addBindLongClickMethod();
    }

    @Override
    public SwipeViewBinderClassStructure addUnbindMethod() {
        return (SwipeViewBinderClassStructure) super.addUnbindMethod();
    }

    public SwipeViewBinderClassStructure addBindSwipeViewMethod() throws IllegalUseException {
        MethodSpec.Builder bindSwipeViewMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_SWIPE_VIEW)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getViewTypeParameterClassName(), NameStore.Variable.ITEM_VIEW);

        for (VariableElement variableElement : getClassDetail().getVariableElements()) {
            SwipeView swipeView = variableElement.getAnnotation(SwipeView.class);
            if (swipeView != null) {
                Validator.validateSwipeView(variableElement);
                bindSwipeViewMethodBuilder.addStatement("$N().$N = $L",
                        NameStore.Method.GET_RESOLVER,
                        variableElement.getSimpleName(),
                        NameStore.Variable.ITEM_VIEW);
            }
        }
        getClassBuilder().addMethod(bindSwipeViewMethodBuilder.build());
        return this;
    }
}
