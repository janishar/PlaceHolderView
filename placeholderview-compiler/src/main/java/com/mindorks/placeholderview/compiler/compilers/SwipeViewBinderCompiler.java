package com.mindorks.placeholderview.compiler.compilers;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeHead;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.mindorks.placeholderview.annotations.swipe.SwipeView;
import com.mindorks.placeholderview.compiler.core.Validator;
import com.mindorks.placeholderview.compiler.RClassBuilder;
import com.mindorks.placeholderview.compiler.structures.SwipeViewBinderClassStructure;

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

public class SwipeViewBinderCompiler extends ViewBinderCompiler {

    public SwipeViewBinderCompiler(Filer filer,
                                   Messager messager,
                                   Elements elementUtils,
                                   RClassBuilder rClassBuilder) {
        super(filer, messager, elementUtils, rClassBuilder);
    }

    @Override
    public boolean compile(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Layout.class)) {
            try {
                SwipeViewBinderClassStructure
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
                        .addBindSwipeViewMethod()
                        .addBindSwipeInMethod()
                        .addBindSwipeOutMethod()
                        .addBindSwipeInStateMethod()
                        .addBindSwipeOutStateMethod()
                        .addBindSwipeCancelStateMethod()
                        .addBindSwipeHeadStateMethod()
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
                SwipeView.class.getCanonicalName(),
                SwipeIn.class.getCanonicalName(),
                SwipeOut.class.getCanonicalName(),
                SwipeInState.class.getCanonicalName(),
                SwipeOutState.class.getCanonicalName(),
                SwipeCancelState.class.getCanonicalName(),
                SwipeHead.class.getCanonicalName()));
        annotations.addAll(super.getSupportedAnnotationTypes());
        return annotations;
    }
}

