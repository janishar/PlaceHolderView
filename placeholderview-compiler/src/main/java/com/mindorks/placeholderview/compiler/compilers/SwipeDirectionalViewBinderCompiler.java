package com.mindorks.placeholderview.compiler.compilers;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.swipe.SwipeInDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeTouch;
import com.mindorks.placeholderview.annotations.swipe.SwipingDirection;
import com.mindorks.placeholderview.compiler.core.Validator;
import com.mindorks.placeholderview.compiler.RClassBuilder;
import com.mindorks.placeholderview.compiler.structures.SwipeDirectionalViewBinderClassStructure;

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

public class SwipeDirectionalViewBinderCompiler extends ViewBinderCompiler {

    public SwipeDirectionalViewBinderCompiler(Filer filer,
                                              Messager messager,
                                              Elements elementUtils,
                                              RClassBuilder rClassBuilder) {
        super(filer, messager, elementUtils, rClassBuilder);
    }

    @Override
    public boolean compile(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Layout.class)) {
            try {
                SwipeDirectionalViewBinderClassStructure
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
                        .addBindSwipingDirectionMethod()
                        .addBindSwipeInDirectionMethod()
                        .addBindSwipeOutDirectionMethod()
                        .addBindSwipeTouchMethod()
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
                SwipeInDirectional.class.getCanonicalName(),
                SwipeOutDirectional.class.getCanonicalName(),
                SwipeTouch.class.getCanonicalName(),
                SwipingDirection.class.getCanonicalName()));
        annotations.addAll(super.getSupportedAnnotationTypes());
        return annotations;
    }
}

