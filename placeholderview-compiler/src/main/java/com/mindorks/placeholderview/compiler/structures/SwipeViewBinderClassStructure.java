package com.mindorks.placeholderview.compiler.structures;

import com.mindorks.placeholderview.annotations.Keep;
import com.mindorks.placeholderview.annotations.internal.BindingSuffix;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeHead;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.mindorks.placeholderview.annotations.swipe.SwipeView;
import com.mindorks.placeholderview.compiler.RClassBuilder;
import com.mindorks.placeholderview.compiler.core.ClassDetail;
import com.mindorks.placeholderview.compiler.core.IllegalUseException;
import com.mindorks.placeholderview.compiler.core.NameStore;
import com.mindorks.placeholderview.compiler.core.Validator;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by janisharali on 28/02/18.
 */

public class SwipeViewBinderClassStructure extends ViewBinderClassStructure {

    public SwipeViewBinderClassStructure(ClassDetail classDetail, RClassBuilder rClassBuilder) {
        super(classDetail, rClassBuilder);
    }

    public static SwipeViewBinderClassStructure create(TypeElement typeElement,
                                                       Elements elementUtils,
                                                       RClassBuilder rClassBuilder) {
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        ClassDetail classDetail = new ClassDetail(
                typeElement,
                packageName,
                NameStore.Class.SWIPE_VIEW_BINDER,
                BindingSuffix.CLASS_SWIPE_VIEW_BINDER_SUFFIX);
        classDetail.changeViewTypeParameterClassName(classDetail.getFrameViewClassName());
        return new SwipeViewBinderClassStructure(classDetail, rClassBuilder);
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
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Keep.class);
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

    public SwipeViewBinderClassStructure addBindSwipeInMethod() throws IllegalUseException {
        MethodSpec.Builder bindSwipeInMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_SWIPE_IN)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            SwipeIn swipeIn = executableElement.getAnnotation(SwipeIn.class);
            if (swipeIn != null) {
                Validator.validateNoParameterMethod(executableElement, "@SwipeIn");
                bindSwipeInMethodBuilder.addStatement("$N.$N()",
                        NameStore.Variable.RESOLVER,
                        executableElement.getSimpleName());
            }
        }
        getClassBuilder().addMethod(bindSwipeInMethodBuilder.build());
        return this;
    }

    public SwipeViewBinderClassStructure addBindSwipeOutMethod() throws IllegalUseException {
        MethodSpec.Builder bindSwipeOutMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_SWIPE_OUT)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            SwipeOut swipeOut = executableElement.getAnnotation(SwipeOut.class);
            if (swipeOut != null) {
                Validator.validateNoParameterMethod(executableElement, "@SwipeOut");
                bindSwipeOutMethodBuilder.addStatement("$N.$N()",
                        NameStore.Variable.RESOLVER,
                        executableElement.getSimpleName());
            }
        }
        getClassBuilder().addMethod(bindSwipeOutMethodBuilder.build());
        return this;
    }

    public SwipeViewBinderClassStructure addBindSwipeInStateMethod() throws IllegalUseException {
        MethodSpec.Builder bindSwipeInStateMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_SWIPE_IN_STATE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            SwipeInState swipeInState = executableElement.getAnnotation(SwipeInState.class);
            if (swipeInState != null) {
                Validator.validateNoParameterMethod(executableElement, "@SwipeInState");
                bindSwipeInStateMethodBuilder.addStatement("$N().$N()",
                        NameStore.Method.GET_RESOLVER,
                        executableElement.getSimpleName());
            }
        }
        getClassBuilder().addMethod(bindSwipeInStateMethodBuilder.build());
        return this;
    }

    public SwipeViewBinderClassStructure addBindSwipeOutStateMethod() throws IllegalUseException {
        MethodSpec.Builder bindSwipeOutStateMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_SWIPE_OUT_STATE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            SwipeOutState swipeOutState = executableElement.getAnnotation(SwipeOutState.class);
            if (swipeOutState != null) {
                Validator.validateNoParameterMethod(executableElement, "@SwipeOutState");
                bindSwipeOutStateMethodBuilder.addStatement("$N().$N()",
                        NameStore.Method.GET_RESOLVER,
                        executableElement.getSimpleName());
            }
        }
        getClassBuilder().addMethod(bindSwipeOutStateMethodBuilder.build());
        return this;
    }

    public SwipeViewBinderClassStructure addBindSwipeCancelStateMethod() throws IllegalUseException {
        MethodSpec.Builder bindSwipeCancelStateMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_SWIPE_CANCEL_STATE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            SwipeCancelState swipeCancelState = executableElement.getAnnotation(SwipeCancelState.class);
            if (swipeCancelState != null) {
                Validator.validateNoParameterMethod(executableElement, "@SwipeCancelState");
                bindSwipeCancelStateMethodBuilder.addStatement("$N().$N()",
                        NameStore.Method.GET_RESOLVER,
                        executableElement.getSimpleName());
            }
        }
        getClassBuilder().addMethod(bindSwipeCancelStateMethodBuilder.build());
        return this;
    }

    public SwipeViewBinderClassStructure addBindSwipeHeadStateMethod() throws IllegalUseException {
        MethodSpec.Builder bindSwipeHeadStateMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_SWIPE_HEAD)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            SwipeHead swipeHead = executableElement.getAnnotation(SwipeHead.class);
            if (swipeHead != null) {
                Validator.validateNoParameterMethod(executableElement, "@SwipeHead");
                bindSwipeHeadStateMethodBuilder.addStatement("$N.$N()",
                        NameStore.Variable.RESOLVER,
                        executableElement.getSimpleName());
            }
        }
        getClassBuilder().addMethod(bindSwipeHeadStateMethodBuilder.build());
        return this;
    }
}
