package com.mindorks.placeholderview.processor;

import com.google.common.collect.ImmutableSet;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.View;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class ViewBinderProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private Elements elementUtils;
    private Validator validator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        validator = new Validator(messager);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Layout.class)) {
            if (!validator.validateTypeElement(element)) {
                return true;
            }

            TypeElement typeElement = (TypeElement) element;
            String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();

            if (!validator.validateLayout(typeElement)) {
                return true;
            }

            ViewBinderGenerator generator = new ViewBinderGenerator(typeElement, packageName);


            classBinder.addMethod(MethodSpec.methodBuilder(NameStore.Method.RECYCLE_VIEW)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .build());

            classBinder.addMethod(MethodSpec.methodBuilder(NameStore.Method.UNBIND)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .build());

            MethodSpec.Builder bindViewPositionMethodBuilder = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_VIEW_POSITION)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER)
                    .addParameter(int.class, NameStore.Variable.POSITION);

            for (VariableElement variableElement : variableElements) {
                Position position = variableElement.getAnnotation(Position.class);
                if (position != null) {
                    bindViewPositionMethodBuilder.addStatement("$N.$N = $L",
                            NameStore.Variable.RESOLVER,
                            variableElement.getSimpleName(),
                            NameStore.Variable.POSITION);
                }
            }
            classBinder.addMethod(bindViewPositionMethodBuilder.build());

            MethodSpec.Builder bindViewMethodBuilder = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_VIEWS)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER)//TODO: Support inner classes import
                    .addParameter(androidViewClassName, NameStore.Variable.ITEM_VIEW);

            for (VariableElement variableElement : variableElements) {
                View view = variableElement.getAnnotation(View.class);
                if (view != null) {
                    if (view.value() <= 0) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "@View should have positive value passed");
                        return true;
                    }
                    bindViewMethodBuilder.addStatement("$N.$N = ($T)$N.findViewById($L)",
                            NameStore.Variable.RESOLVER,
                            variableElement.getSimpleName(),
                            variableElement,
                            NameStore.Variable.ITEM_VIEW,
                            view.value());
                }
            }
            classBinder.addMethod(bindViewMethodBuilder.build());

            MethodSpec.Builder bindClickMethodBuilder = MethodSpec
                    .methodBuilder(NameStore.Method.BIND_CLICK)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER, Modifier.FINAL)
                    .addParameter(androidViewClassName, NameStore.Variable.ITEM_VIEW);

            ClassName androidViewOnClickListenerClassName = ClassName.get(
                    NameStore.Package.ANDROID_VIEW,
                    NameStore.Class.ANDROID_VIEW,
                    NameStore.Class.ANDROID_VIEW_ON_CLICK_LISTENER);

            for (ExecutableElement executableElement : executableElements) {
                Click click = executableElement.getAnnotation(Click.class);
                if (click != null) {
                    if (click.value() <= 0) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "@Click should have positive value passed");
                        return true;
                    }

                    TypeSpec OnClickListenerClass = TypeSpec.anonymousClassBuilder("")
                            .addSuperinterface(androidViewOnClickListenerClassName)
                            .addMethod(MethodSpec.methodBuilder(NameStore.Method.ANDROID_VIEW_ON_CLICK)
                                    .addModifiers(Modifier.PUBLIC)
                                    .addAnnotation(Override.class)
                                    .addParameter(androidViewClassName, NameStore.Variable.ANDROID_VIEW)
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
        return ImmutableSet.of(
                Layout.class.getCanonicalName(),
                NonReusable.class.getCanonicalName(),
                Position.class.getCanonicalName(),
                View.class.getCanonicalName(),
                Click.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}

