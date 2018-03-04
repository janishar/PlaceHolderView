package com.mindorks.placeholderview.compiler.compilers;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Recycle;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.compiler.core.Compiler;
import com.mindorks.placeholderview.compiler.core.Validator;
import com.mindorks.placeholderview.compiler.RClassBuilder;
import com.mindorks.placeholderview.compiler.structures.ViewBinderClassStructure;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class ViewBinderCompiler extends Compiler {

    public ViewBinderCompiler(Filer filer,
                              Messager messager,
                              Elements elementUtils,
                              RClassBuilder rClassBuilder) {
        super(filer, messager, elementUtils, rClassBuilder);
    }

    @Override
    public boolean compile(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Layout.class)) {
            try {
                ViewBinderClassStructure
                        .create(Validator.validateLayout((TypeElement) Validator.validateTypeElement(element)),
                                getElementUtils(),
                                getRClassBuilder())
                        .addConstructor()
                        .addResolveViewMethod()
                        .addRecycleViewMethod()
                        .addUnbindMethod()
                        .addBindViewPositionMethod()
                        .addBindViewMethod()
                        .addBindClickMethod()
                        .addBindLongClickMethod()
                        .prepare()
                        .generate(getFiler());
            } catch (IOException e) {
                getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString(), element);
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new TreeSet<>(Arrays.asList(
                Layout.class.getCanonicalName(),
                NonReusable.class.getCanonicalName(),
                Position.class.getCanonicalName(),
                View.class.getCanonicalName(),
                Resolve.class.getCanonicalName(),
                Recycle.class.getCanonicalName(),
                Click.class.getCanonicalName(),
                LongClick.class.getCanonicalName()));
    }
}

