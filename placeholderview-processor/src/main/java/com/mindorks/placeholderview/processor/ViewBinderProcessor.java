package com.mindorks.placeholderview.processor;

import com.google.common.collect.ImmutableSet;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.internal.BindingSuffix;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class ViewBinderProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Layout.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Can be applied to class.");
                return true;
            }

            TypeElement typeElement = (TypeElement) element;
            String typeName = element.getSimpleName().toString();
            String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            ClassName className = ClassName.get(packageName, typeName);

            ClassName binderClassName =
                    ClassName.get(packageName, typeName + BindingSuffix.CLASS_VIEW_BINDER_SUFFIX);

            Layout layout = element.getAnnotation(Layout.class);
            if (layout.value() <= 0) {
                messager.printMessage(Diagnostic.Kind.ERROR,
                        "@Layout should have positive value passed");
                return true;
            }

            ClassName viewBinderClassName = ClassName.get(NameStore.Package.PLACE_HOLDER_VIEW, NameStore.Class.VIEW_BINDER);
            ClassName androidViewClassName = ClassName.get(NameStore.Package.ANDROID_VIEW, NameStore.Class.ANDROID_VIEW);

            TypeSpec.Builder classBinder = TypeSpec
                    .classBuilder(binderClassName)
                    .superclass(ParameterizedTypeName.get(
                            viewBinderClassName,
                            TypeVariableName.get(typeElement.asType()),
                            TypeVariableName.get(androidViewClassName.simpleName())))
                    .addModifiers(Modifier.PUBLIC);

            MethodSpec classBinderConstructor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PROTECTED)
                    .addParameter(className, NameStore.Variable.RESOLVER)
                    .addStatement("super($N)", NameStore.Variable.RESOLVER)
                    .build();
            classBinder.addMethod(classBinderConstructor);

            MethodSpec bindLayoutMethod = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_LAYOUT)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER)
                    .build();
            classBinder.addMethod(bindLayoutMethod);

            MethodSpec getNullableMethod = MethodSpec
                    .methodBuilder(NameStore.Method.GET_NULLABLE)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER)
                    .build();
            classBinder.addMethod(getNullableMethod);

            MethodSpec bindViewMethod = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_VIEWS)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER)//TODO: Support inner classes import
                    .addParameter(androidViewClassName, NameStore.Variable.ITEM_VIEW)
//                    .addStatement("$T $N = $N$N($L)", variableElement, "view", LAYOUT_VIEW_VARIABLE_NAME, ".findViewById", view.value())
//                    .addStatement("$N.$N=$N", "resolver", variableElement.getSimpleName(), "view")
                    .build();
            classBinder.addMethod(bindViewMethod);

            MethodSpec bindViewPositionMethod = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_VIEW_POSITION)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER)
                    .addParameter(int.class, NameStore.Variable.POSITION)
                    .build();
            classBinder.addMethod(bindViewPositionMethod);

            MethodSpec resolveViewMethod = MethodSpec
                    .methodBuilder(NameStore.Method.RESOLVE_VIEW)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER)
                    .build();
            classBinder.addMethod(resolveViewMethod);

            MethodSpec bindClickMethod = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_CLICK)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER)
                    .addParameter(androidViewClassName, NameStore.Variable.ITEM_VIEW)
                    .build();
            classBinder.addMethod(bindClickMethod);

            MethodSpec bindLongPressMethod = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_LONG_PRESS)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER)
                    .addParameter(androidViewClassName, NameStore.Variable.ITEM_VIEW)
                    .build();
            classBinder.addMethod(bindLongPressMethod);

            MethodSpec recycleViewMethod = MethodSpec
                    .methodBuilder(NameStore.Method.RECYCLE_VIEW)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .build();
            classBinder.addMethod(recycleViewMethod);

            MethodSpec unbindMethod = MethodSpec
                    .methodBuilder(NameStore.Method.UNBIND)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .build();
            classBinder.addMethod(unbindMethod);

            try {
                JavaFile.builder(packageName, classBinder.build())
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(Layout.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}

