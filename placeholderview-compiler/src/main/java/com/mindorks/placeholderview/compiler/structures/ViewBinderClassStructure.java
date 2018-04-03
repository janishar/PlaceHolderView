package com.mindorks.placeholderview.compiler.structures;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Keep;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Recycle;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.internal.BindingSuffix;
import com.mindorks.placeholderview.compiler.RClassBuilder;
import com.mindorks.placeholderview.compiler.core.ClassDetail;
import com.mindorks.placeholderview.compiler.core.ClassStructure;
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
 * Created by ali on 27/02/18.
 */
public class ViewBinderClassStructure extends ClassStructure {

    private RClassBuilder rClassBuilder;

    public ViewBinderClassStructure(ClassDetail classDetail, RClassBuilder rClassBuilder) {
        super(classDetail);
        this.rClassBuilder = rClassBuilder;
    }

    public static ViewBinderClassStructure create(TypeElement typeElement,
                                                  Elements elementUtils,
                                                  RClassBuilder rClassBuilder) {
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        return new ViewBinderClassStructure(new ClassDetail(
                typeElement,
                packageName,
                NameStore.Class.VIEW_BINDER,
                BindingSuffix.CLASS_VIEW_BINDER_SUFFIX),
                rClassBuilder);
    }

    @Override
    protected TypeSpec.Builder createClassBuilder() {
        return TypeSpec.classBuilder(getClassDetail().getGeneratedClassName())
                .superclass(ParameterizedTypeName.get(
                        getClassDetail().getSuperClassName(),
                        TypeVariableName.get(getClassDetail().getTypeElement().asType()),
                        TypeVariableName.get(getClassDetail().getViewTypeParameterClassName().simpleName())))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Keep.class);
    }

    public ViewBinderClassStructure addConstructor() {
        Layout layout = getClassDetail().getTypeElement().getAnnotation(Layout.class);
        NonReusable nonReusable = getClassDetail().getTypeElement().getAnnotation(NonReusable.class);
        boolean nullable = nonReusable != null;
        getClassBuilder()
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER)
                        .addStatement("super($N, $T.$L, $L)",
                                NameStore.Variable.RESOLVER,
                                getRClassBuilder().getLayoutClassName(),
                                getRClassBuilder().addLayoutId(getClassDetail().getTypeElement(), layout.value()),
                                nullable)
                        .build());
        return this;
    }

    public ViewBinderClassStructure addResolveViewMethod() throws IllegalUseException {
        MethodSpec.Builder bindResolveMethodBuilder = MethodSpec.methodBuilder(NameStore.Method.RESOLVE_VIEW)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            Resolve resolve = executableElement.getAnnotation(Resolve.class);
            if (resolve != null) {
                Validator.validateResolve(executableElement);
                bindResolveMethodBuilder.addStatement("$N.$N()",
                        NameStore.Variable.RESOLVER,
                        executableElement.getSimpleName());
            }
        }
        getClassBuilder().addMethod(bindResolveMethodBuilder.build());
        return this;
    }

    public ViewBinderClassStructure addRecycleViewMethod() throws IllegalUseException {
        MethodSpec.Builder bindRecycleMethodBuilder = MethodSpec.methodBuilder(NameStore.Method.RECYCLE_VIEW)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addStatement("$T $N = $N()",
                        getClassDetail().getClassName(),
                        NameStore.Variable.RESOLVER,
                        NameStore.Method.GET_RESOLVER)
                .returns(void.class);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            Recycle recycle = executableElement.getAnnotation(Recycle.class);
            if (recycle != null) {
                Validator.validateRecycle(executableElement);
                bindRecycleMethodBuilder.addStatement("$N.$N()",
                        NameStore.Variable.RESOLVER,
                        executableElement.getSimpleName());
            }
        }
        getClassBuilder().addMethod(bindRecycleMethodBuilder.build());
        return this;
    }

    public ViewBinderClassStructure addBindViewPositionMethod() throws IllegalUseException {
        MethodSpec.Builder bindViewPositionMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_VIEW_POSITION)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER)
                .addParameter(int.class, NameStore.Variable.POSITION);

        for (VariableElement variableElement : getClassDetail().getVariableElements()) {
            Position position = variableElement.getAnnotation(Position.class);
            if (position != null) {
                Validator.validatePosition(variableElement);
                bindViewPositionMethodBuilder.addStatement("$N.$N = $L",
                        NameStore.Variable.RESOLVER,
                        variableElement.getSimpleName(),
                        NameStore.Variable.POSITION);
            }
        }
        getClassBuilder().addMethod(bindViewPositionMethodBuilder.build());
        return this;
    }

    public ViewBinderClassStructure addBindViewMethod() throws IllegalUseException {
        MethodSpec.Builder bindViewMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_VIEWS)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER)//TODO: Support inner classes import
                .addParameter(getClassDetail().getViewTypeParameterClassName(), NameStore.Variable.ITEM_VIEW);

        for (VariableElement variableElement : getClassDetail().getVariableElements()) {
            View view = variableElement.getAnnotation(View.class);
            if (view != null) {
                Validator.validateView(variableElement, view);
                bindViewMethodBuilder.addStatement("$N.$N = ($T)$N.findViewById($T.$L)",
                        NameStore.Variable.RESOLVER,
                        variableElement.getSimpleName(),
                        variableElement,
                        NameStore.Variable.ITEM_VIEW,
                        getRClassBuilder().getIdClassName(),
                        getRClassBuilder().addViewId(variableElement, view.value()));
            }
        }
        getClassBuilder().addMethod(bindViewMethodBuilder.build());
        return this;
    }

    public ViewBinderClassStructure addBindClickMethod() throws IllegalUseException {
        MethodSpec.Builder bindClickMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_CLICK)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER, Modifier.FINAL)
                .addParameter(getClassDetail().getViewTypeParameterClassName(), NameStore.Variable.ITEM_VIEW);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            Click click = executableElement.getAnnotation(Click.class);
            if (click != null) {
                Validator.validateClick(executableElement, click);
                TypeSpec OnClickListenerClass = TypeSpec.anonymousClassBuilder("")
                        .addSuperinterface(getClassDetail().getAndroidOnClickListenerClassName())
                        .addMethod(MethodSpec.methodBuilder(NameStore.Method.ANDROID_VIEW_ON_CLICK)
                                .addModifiers(Modifier.PUBLIC)
                                .addAnnotation(Override.class)
                                .addParameter(getClassDetail().getAndroidViewClassName(), NameStore.Variable.ANDROID_VIEW)
                                .addStatement("$N.$N()", NameStore.Variable.RESOLVER, executableElement.getSimpleName())
                                .returns(void.class)
                                .build())
                        .build();
                bindClickMethodBuilder.addStatement("$N.findViewById($T.$L).setOnClickListener($L)",
                        NameStore.Variable.ITEM_VIEW,
                        getRClassBuilder().getIdClassName(),
                        getRClassBuilder().addViewId(executableElement, click.value()),
                        OnClickListenerClass);
            }
        }
        getClassBuilder().addMethod(bindClickMethodBuilder.build());
        return this;
    }

    public ViewBinderClassStructure addBindLongClickMethod() throws IllegalUseException {
        MethodSpec.Builder bindLongClickMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_LONG_CLICK)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER, Modifier.FINAL)
                .addParameter(getClassDetail().getViewTypeParameterClassName(), NameStore.Variable.ITEM_VIEW);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            LongClick longClick = executableElement.getAnnotation(LongClick.class);
            if (longClick != null) {
                Validator.validateLongClick(executableElement, longClick);
                TypeSpec OnClickListenerClass = TypeSpec.anonymousClassBuilder("")
                        .addSuperinterface(getClassDetail().getAndroidOnLongClickListenerClassName())
                        .addMethod(MethodSpec.methodBuilder(NameStore.Method.ANDROID_VIEW_ON_LONG_CLICK)
                                .addModifiers(Modifier.PUBLIC)
                                .addAnnotation(Override.class)
                                .addParameter(getClassDetail().getAndroidViewClassName(), NameStore.Variable.ANDROID_VIEW)
                                .addStatement("$N.$N()", NameStore.Variable.RESOLVER, executableElement.getSimpleName())
                                .addStatement("$N $L", "return", true)
                                .returns(boolean.class)
                                .build())
                        .build();
                bindLongClickMethodBuilder.addStatement("$N.findViewById($T.$L).setOnLongClickListener($L)",
                        NameStore.Variable.ITEM_VIEW,
                        getRClassBuilder().getIdClassName(),
                        getRClassBuilder().addViewId(executableElement, longClick.value()),
                        OnClickListenerClass);
            }
        }
        getClassBuilder().addMethod(bindLongClickMethodBuilder.build());
        return this;
    }

    public ViewBinderClassStructure addUnbindMethod() {
        MethodSpec.Builder unbindMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.UNBIND)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addStatement("$T $N = $N()",
                        getClassDetail().getClassName(),
                        NameStore.Variable.RESOLVER,
                        NameStore.Method.GET_RESOLVER)
                .addStatement("$L $N = $N()",
                        boolean.class,
                        NameStore.Variable.NULLABLE,
                        NameStore.Method.IS_NULLABLE)
                .beginControlFlow("if ($N != $L && $N)",
                        NameStore.Variable.RESOLVER,
                        null,
                        NameStore.Variable.NULLABLE);

        for (VariableElement variableElement : getClassDetail().getVariableElements()) {
            if (!variableElement.asType().getKind().isPrimitive()
                    && !variableElement.getModifiers().contains(Modifier.PRIVATE)) {
                unbindMethodBuilder.addStatement("$N.$N = $L",
                        NameStore.Variable.RESOLVER,
                        variableElement.getSimpleName(),
                        null);
            }
        }
        unbindMethodBuilder
                .addStatement("$N($L)", NameStore.Method.SET_RESOLVER, null)
                .addStatement("$N($L)", NameStore.Method.SET_ANIMATION_RESOLVER, null)
                .endControlFlow()
                .returns(void.class);
        getClassBuilder().addMethod(unbindMethodBuilder.build());
        return this;
    }

    public RClassBuilder getRClassBuilder() {
        return rClassBuilder;
    }
}
