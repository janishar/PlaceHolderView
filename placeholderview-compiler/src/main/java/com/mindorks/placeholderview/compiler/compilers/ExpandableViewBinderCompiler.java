package com.mindorks.placeholderview.compiler.compilers;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.mindorks.placeholderview.annotations.expand.Toggle;
import com.mindorks.placeholderview.compiler.core.Validator;
import com.mindorks.placeholderview.compiler.structures.ExpandableViewBinderClassStructure;
import com.mindorks.placeholderview.compiler.RClassBuilder;

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

public class ExpandableViewBinderCompiler extends ViewBinderCompiler {

    public ExpandableViewBinderCompiler(Filer filer,
                                        Messager messager,
                                        Elements elementUtils,
                                        RClassBuilder rClassBuilder) {
        super(filer, messager, elementUtils, rClassBuilder);
    }

    @Override
    public boolean compile(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Layout.class)) {
            try {
                ExpandableViewBinderClassStructure
                        .create(Validator.validateLayout((TypeElement) Validator.validateTypeElement(element)),
                                getElementUtils(),
                                getRClassBuilder())
                        .addConstructor()
                        .addResolveViewMethod()
                        .addRecycleViewMethod()
                        .addUnbindMethod()
                        .addBindAnimationMethod()
                        .addBindViewPositionMethod()
                        .addBindViewMethod()
                        .addBindClickMethod()
                        .addBindLongClickMethod()
                        .addBindParentPositionMethod()
                        .addBindChildPositionMethod()
                        .addBindToggleMethod()
                        .addBindExpandMethod()
                        .addBindCollapseMethod()
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
        Set<String> annotations = new TreeSet<>(Arrays.asList(
                ChildPosition.class.getCanonicalName(),
                ParentPosition.class.getCanonicalName(),
                Collapse.class.getCanonicalName(),
                Expand.class.getCanonicalName(),
                Parent.class.getCanonicalName(),
                Toggle.class.getCanonicalName(),
                SingleTop.class.getCanonicalName()));
        annotations.addAll(super.getSupportedAnnotationTypes());
        return annotations;
    }
}

