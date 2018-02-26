package com.mindorks.placeholderview.processor;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Recycle;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by ali on 26/02/18.
 */

public class Generator {

    private Elements elementUtils;
    private Filer filer;

    private Generator(Elements elementUtils, Filer filer) {
        this.elementUtils = elementUtils;
        this.filer = filer;
    }

    public static Generator create(Elements elementUtils, Filer filer) {
        return new Generator(elementUtils, filer);
    }

    public ClassStructure classStructure(TypeElement typeElement, String classNameSuffix) {
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        return new ClassStructure(ClassDetail.build(typeElement, packageName, classNameSuffix));
    }

    public class ClassGenerator {
        private ClassStructure classStructure;

        public ClassGenerator(ClassStructure classStructure) {
            this.classStructure = classStructure;
        }

        public void generate() throws IOException {
            JavaFile.builder(classStructure.classDetail.getPackageName(), classStructure.classBinder.build())
                    .build()
                    .writeTo(filer);
        }
    }

    public class ClassStructure {
        private ClassDetail classDetail;
        private TypeSpec.Builder classBinder;

        public ClassStructure(ClassDetail classDetail) {
            this.classDetail = classDetail;
            classBinder = TypeSpec.classBuilder(classDetail.getBinderClassName())
                    .superclass(ParameterizedTypeName.get(
                            classDetail.getViewBinderClassName(),
                            TypeVariableName.get(classDetail.getTypeElement().asType()),
                            TypeVariableName.get(classDetail.getAndroidViewClassName().simpleName())))
                    .addModifiers(Modifier.PUBLIC);
        }

        public ClassStructure addConstructor() {
            Layout layout = classDetail.getTypeElement().getAnnotation(Layout.class);
            NonReusable nonReusable = classDetail.getTypeElement().getAnnotation(NonReusable.class);
            boolean nullable = nonReusable != null;
            classBinder.addMethod(MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PROTECTED)
                    .addParameter(classDetail.getClassName(), NameStore.Variable.RESOLVER)
                    .addStatement("super($N, $L, $L)", NameStore.Variable.RESOLVER, layout.value(), nullable)
                    .build());
            return this;
        }

        public ClassStructure addResolveViewMethod() throws IllegalUseException {
            MethodSpec.Builder bindResolveMethodBuilder = MethodSpec.methodBuilder(NameStore.Method.RESOLVE_VIEW)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(classDetail.getClassName(), NameStore.Variable.RESOLVER);

            for (ExecutableElement executableElement : classDetail.getExecutableElements()) {
                Resolve resolve = executableElement.getAnnotation(Resolve.class);
                if (resolve != null) {
                    Validator.validateResolve(executableElement);
                    bindResolveMethodBuilder.addStatement("$N.$N()",
                            NameStore.Variable.RESOLVER,
                            executableElement.getSimpleName());
                }
            }
            classBinder.addMethod(bindResolveMethodBuilder.build());
            return this;
        }

        public ClassStructure addRecycleViewMethod() throws IllegalUseException {
            MethodSpec.Builder bindRecycleMethodBuilder = MethodSpec.methodBuilder(NameStore.Method.RECYCLE_VIEW)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .addStatement("$T $N = $N()",
                            classDetail.getClassName(),
                            NameStore.Variable.RESOLVER,
                            NameStore.Method.GET_RESOLVER)
                    .returns(void.class);

            for (ExecutableElement executableElement : classDetail.getExecutableElements()) {
                Recycle recycle = executableElement.getAnnotation(Recycle.class);
                if (recycle != null) {
                    Validator.validateRecycle(executableElement);
                    bindRecycleMethodBuilder.addStatement("$N.$N()",
                            NameStore.Variable.RESOLVER,
                            executableElement.getSimpleName());
                }
            }
            classBinder.addMethod(bindRecycleMethodBuilder.build());
            return this;
        }

        public ClassStructure addBindViewPositionMethod() throws IllegalUseException {
            MethodSpec.Builder bindViewPositionMethodBuilder = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_VIEW_POSITION)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(classDetail.getClassName(), NameStore.Variable.RESOLVER)
                    .addParameter(int.class, NameStore.Variable.POSITION);

            for (VariableElement variableElement : classDetail.getVariableElements()) {
                Position position = variableElement.getAnnotation(Position.class);
                if (position != null) {
                    Validator.validatePosition(variableElement);
                    bindViewPositionMethodBuilder.addStatement("$N.$N = $L",
                            NameStore.Variable.RESOLVER,
                            variableElement.getSimpleName(),
                            NameStore.Variable.POSITION);
                }
            }
            classBinder.addMethod(bindViewPositionMethodBuilder.build());
            return this;
        }

        public ClassStructure addBindViewMethod() throws IllegalUseException {
            MethodSpec.Builder bindViewMethodBuilder = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_VIEWS)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(classDetail.getClassName(), NameStore.Variable.RESOLVER)//TODO: Support inner classes import
                    .addParameter(classDetail.getAndroidViewClassName(), NameStore.Variable.ITEM_VIEW);

            for (VariableElement variableElement : classDetail.getVariableElements()) {
                View view = variableElement.getAnnotation(View.class);
                if (view != null) {
                    Validator.validateView(variableElement, view);
                    bindViewMethodBuilder.addStatement("$N.$N = ($T)$N.findViewById($L)",
                            NameStore.Variable.RESOLVER,
                            variableElement.getSimpleName(),
                            variableElement,
                            NameStore.Variable.ITEM_VIEW,
                            view.value());
                }
            }
            classBinder.addMethod(bindViewMethodBuilder.build());
            return this;
        }

        public ClassStructure addBindClickMethod() throws IllegalUseException {
            MethodSpec.Builder bindClickMethodBuilder = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_CLICK)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(classDetail.getClassName(), NameStore.Variable.RESOLVER, Modifier.FINAL)
                    .addParameter(classDetail.getAndroidViewClassName(), NameStore.Variable.ITEM_VIEW);

            for (ExecutableElement executableElement : classDetail.getExecutableElements()) {
                Click click = executableElement.getAnnotation(Click.class);
                if (click != null) {
                    Validator.validateClick(executableElement, click);
                    TypeSpec OnClickListenerClass = TypeSpec.anonymousClassBuilder("")
                            .addSuperinterface(classDetail.getAndroidOnClickListenerClassName())
                            .addMethod(MethodSpec.methodBuilder(NameStore.Method.ANDROID_VIEW_ON_CLICK)
                                    .addModifiers(Modifier.PUBLIC)
                                    .addAnnotation(Override.class)
                                    .addParameter(classDetail.getAndroidViewClassName(), NameStore.Variable.ANDROID_VIEW)
                                    .addStatement("$N.$N()", NameStore.Variable.RESOLVER, executableElement.getSimpleName())
                                    .returns(void.class)
                                    .build())
                            .build();
                    bindClickMethodBuilder.addStatement("$N.findViewById($L).setOnClickListener($L)",
                            NameStore.Variable.ITEM_VIEW,
                            click.value(),
                            OnClickListenerClass);
                }
            }
            classBinder.addMethod(bindClickMethodBuilder.build());
            return this;
        }

        public ClassStructure addBindLongClickMethod() throws IllegalUseException {
            MethodSpec.Builder bindLongClickMethodBuilder = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_LONG_CLICK)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(classDetail.getClassName(), NameStore.Variable.RESOLVER, Modifier.FINAL)
                    .addParameter(classDetail.getAndroidViewClassName(), NameStore.Variable.ITEM_VIEW);

            for (ExecutableElement executableElement : classDetail.getExecutableElements()) {
                LongClick longClick = executableElement.getAnnotation(LongClick.class);
                if (longClick != null) {
                    Validator.validateLongClick(executableElement, longClick);
                    TypeSpec OnClickListenerClass = TypeSpec.anonymousClassBuilder("")
                            .addSuperinterface(classDetail.getAndroidOnLongClickListenerClassName())
                            .addMethod(MethodSpec.methodBuilder(NameStore.Method.ANDROID_VIEW_ON_LONG_CLICK)
                                    .addModifiers(Modifier.PUBLIC)
                                    .addAnnotation(Override.class)
                                    .addParameter(classDetail.getAndroidViewClassName(), NameStore.Variable.ANDROID_VIEW)
                                    .addStatement("$N.$N()", NameStore.Variable.RESOLVER, executableElement.getSimpleName())
                                    .addStatement("$N $L", "return", true)
                                    .returns(boolean.class)
                                    .build())
                            .build();
                    bindLongClickMethodBuilder.addStatement("$N.findViewById($L).setOnLongClickListener($L)",
                            NameStore.Variable.ITEM_VIEW,
                            longClick.value(),
                            OnClickListenerClass);
                }
            }
            classBinder.addMethod(bindLongClickMethodBuilder.build());
            return this;
        }

        public ClassStructure addUnbindMethod() {
            MethodSpec.Builder bindViewPositionMethodBuilder = MethodSpec
                    .methodBuilder(NameStore.Method.UNBIND)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .addStatement("$T $N = $N()",
                            classDetail.getClassName(),
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

            for (VariableElement variableElement : classDetail.getVariableElements()) {
                if (!variableElement.asType().getKind().isPrimitive()
                        && !variableElement.getModifiers().contains(Modifier.PRIVATE)) {
                    bindViewPositionMethodBuilder.addStatement("$N.$N = $L",
                            NameStore.Variable.RESOLVER,
                            variableElement.getSimpleName(),
                            null);
                }
            }
            bindViewPositionMethodBuilder
                    .endControlFlow()
                    .returns(void.class);
            classBinder.addMethod(bindViewPositionMethodBuilder.build());
            return this;
        }

        public ClassGenerator prepare() {
            return new ClassGenerator(this);
        }
    }
}
