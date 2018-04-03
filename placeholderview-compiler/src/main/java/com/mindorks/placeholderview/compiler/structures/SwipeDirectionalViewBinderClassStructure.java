package com.mindorks.placeholderview.compiler.structures;

import com.mindorks.placeholderview.annotations.Keep;
import com.mindorks.placeholderview.annotations.internal.BindingSuffix;
import com.mindorks.placeholderview.annotations.swipe.SwipeInDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeTouch;
import com.mindorks.placeholderview.annotations.swipe.SwipingDirection;
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
import javax.lang.model.util.Elements;

/**
 * Created by janisharali on 28/02/18.
 */

public class SwipeDirectionalViewBinderClassStructure extends SwipeViewBinderClassStructure {

    public SwipeDirectionalViewBinderClassStructure(ClassDetail classDetail, RClassBuilder rClassBuilder) {
        super(classDetail, rClassBuilder);
    }

    public static SwipeDirectionalViewBinderClassStructure create(TypeElement typeElement,
                                                                  Elements elementUtils,
                                                                  RClassBuilder rClassBuilder) {
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        ClassDetail classDetail = new ClassDetail(
                typeElement,
                packageName,
                NameStore.Class.SWIPE_DIRECTIONAL_VIEW_BINDER,
                BindingSuffix.CLASS_SWIPE_DIRECTIONAL_VIEW_BINDER_SUFFIX);
        classDetail.changeViewTypeParameterClassName(classDetail.getFrameViewClassName());
        return new SwipeDirectionalViewBinderClassStructure(classDetail, rClassBuilder);
    }

    @Override
    protected TypeSpec.Builder createClassBuilder() {
        return TypeSpec.classBuilder(getClassDetail().getGeneratedClassName())
                .superclass(ParameterizedTypeName.get(
                        getClassDetail().getSuperClassName(),
                        TypeVariableName.get(getClassDetail().getTypeElement().asType()),
                        getClassDetail().getFrameViewClassName(),
                        getClassDetail().getSwipeDirectionalOptionClassName(),
                        getClassDetail().getSwipeDecorClassName()))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Keep.class);
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addConstructor() {
        return (SwipeDirectionalViewBinderClassStructure) super.addConstructor();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addResolveViewMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addResolveViewMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addRecycleViewMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addRecycleViewMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addBindViewPositionMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addBindViewPositionMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addBindViewMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addBindViewMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addBindClickMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addBindClickMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addBindLongClickMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addBindLongClickMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addUnbindMethod() {
        return (SwipeDirectionalViewBinderClassStructure) super.addUnbindMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addBindSwipeViewMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addBindSwipeViewMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addBindSwipeInMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addBindSwipeInMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addBindSwipeOutMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addBindSwipeOutMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addBindSwipeInStateMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addBindSwipeInStateMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addBindSwipeOutStateMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addBindSwipeOutStateMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addBindSwipeCancelStateMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addBindSwipeCancelStateMethod();
    }

    @Override
    public SwipeDirectionalViewBinderClassStructure addBindSwipeHeadStateMethod() throws IllegalUseException {
        return (SwipeDirectionalViewBinderClassStructure) super.addBindSwipeHeadStateMethod();
    }

    public SwipeDirectionalViewBinderClassStructure addBindSwipingDirectionMethod() throws IllegalUseException {
        MethodSpec.Builder bindSwipingDirectionMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_SWIPING_DIRECTION)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getSwipeDirectionClassName(), NameStore.Variable.DIRECTION);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            SwipingDirection direction = executableElement.getAnnotation(SwipingDirection.class);
            if (direction != null) {
                Validator.validateSwipeDirection(executableElement, "SwipeDirection");
                bindSwipingDirectionMethodBuilder.addStatement("$N().$N($N)",
                        NameStore.Method.GET_RESOLVER,
                        executableElement.getSimpleName(),
                        NameStore.Variable.DIRECTION);
            }
        }
        getClassBuilder().addMethod(bindSwipingDirectionMethodBuilder.build());
        return this;
    }

    public SwipeDirectionalViewBinderClassStructure addBindSwipeInDirectionMethod() throws IllegalUseException {
        MethodSpec.Builder bindSwipeInDirectionMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_SWIPE_IN_DIRECTION)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getSwipeDirectionClassName(), NameStore.Variable.DIRECTION);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            SwipeInDirectional direction = executableElement.getAnnotation(SwipeInDirectional.class);
            if (direction != null) {
                Validator.validateSwipeDirection(executableElement, "SwipeInDirectional");
                bindSwipeInDirectionMethodBuilder.addStatement("$N().$N($N)",
                        NameStore.Method.GET_RESOLVER,
                        executableElement.getSimpleName(),
                        NameStore.Variable.DIRECTION);
            }
        }
        getClassBuilder().addMethod(bindSwipeInDirectionMethodBuilder.build());
        return this;
    }

    public SwipeDirectionalViewBinderClassStructure addBindSwipeOutDirectionMethod() throws IllegalUseException {
        MethodSpec.Builder bindSwipeOutDirectionMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_SWIPE_OUT_DIRECTION)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getSwipeDirectionClassName(), NameStore.Variable.DIRECTION);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            SwipeOutDirectional direction = executableElement.getAnnotation(SwipeOutDirectional.class);
            if (direction != null) {
                Validator.validateSwipeDirection(executableElement, "SwipeOutDirectional");
                bindSwipeOutDirectionMethodBuilder.addStatement("$N().$N($N)",
                        NameStore.Method.GET_RESOLVER,
                        executableElement.getSimpleName(),
                        NameStore.Variable.DIRECTION);
            }
        }
        getClassBuilder().addMethod(bindSwipeOutDirectionMethodBuilder.build());
        return this;
    }

    public SwipeDirectionalViewBinderClassStructure addBindSwipeTouchMethod() throws IllegalUseException {
        MethodSpec.Builder bindSwipeTouchMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_SWIPE_TOUCH)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(float.class, NameStore.Variable.X_START)
                .addParameter(float.class, NameStore.Variable.Y_START)
                .addParameter(float.class, NameStore.Variable.X_CURRENT)
                .addParameter(float.class, NameStore.Variable.Y_CURRENT);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            SwipeTouch touch = executableElement.getAnnotation(SwipeTouch.class);
            if (touch != null) {
                Validator.validateSwipeTouch(executableElement);
                bindSwipeTouchMethodBuilder.addStatement("$N().$N($N, $N, $N, $N)",
                        NameStore.Method.GET_RESOLVER,
                        executableElement.getSimpleName(),
                        NameStore.Variable.X_START,
                        NameStore.Variable.Y_START,
                        NameStore.Variable.X_CURRENT,
                        NameStore.Variable.Y_CURRENT);
            }
        }
        getClassBuilder().addMethod(bindSwipeTouchMethodBuilder.build());
        return this;
    }
}
