package com.mindorks.placeholderview.processor;

import com.google.common.collect.ImmutableSet;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.internal.BindingSuffix;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
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
            try {
                Generator.create(elementUtils, filer)
                        .classStructure(
                                Validator.validateLayout((TypeElement) Validator.validateTypeElement(element)),
                                BindingSuffix.CLASS_VIEW_BINDER_SUFFIX)
                        .addConstructor()
                        .addResolveViewMethod()
                        .addRecycleViewMethod()
                        .addUnbindMethod()
                        .addBindViewPositionMethod()
                        .addBindViewMethod()
                        .addBindClickMethod()
                        .addBindLongClickMethod()
                        .prepare()
                        .generate();
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, e.toString(), element);
                return true;
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

