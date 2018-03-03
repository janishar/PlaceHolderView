package com.mindorks.placeholderview.compiler;

import com.mindorks.placeholderview.compiler.compilers.ExpandableViewBinderCompiler;
import com.mindorks.placeholderview.compiler.compilers.SwipeDirectionalViewBinderCompiler;
import com.mindorks.placeholderview.compiler.compilers.SwipeViewBinderCompiler;
import com.mindorks.placeholderview.compiler.compilers.ViewBinderCompiler;

import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by janisharali on 04/03/18.
 */

public class PlaceHolderViewProcessor extends AbstractProcessor {

    private ViewBinderCompiler mViewBinderCompiler;
    private ExpandableViewBinderCompiler mExpandableViewBinderCompiler;
    private SwipeViewBinderCompiler mSwipeViewBinderCompiler;
    private SwipeDirectionalViewBinderCompiler mSwipeDirectionalViewBinderCompiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        Filer filer = processingEnv.getFiler();
        Messager messager = processingEnv.getMessager();
        Elements elementUtils = processingEnv.getElementUtils();

        mViewBinderCompiler = new ViewBinderCompiler(filer, messager, elementUtils);
        mExpandableViewBinderCompiler = new ExpandableViewBinderCompiler(filer, messager, elementUtils);
        mSwipeViewBinderCompiler = new SwipeViewBinderCompiler(filer, messager, elementUtils);
        mSwipeDirectionalViewBinderCompiler = new SwipeDirectionalViewBinderCompiler(filer, messager, elementUtils);

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return mViewBinderCompiler.compile(roundEnv)
                && mExpandableViewBinderCompiler.compile(roundEnv)
                && mSwipeViewBinderCompiler.compile(roundEnv)
                && mSwipeDirectionalViewBinderCompiler.compile(roundEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new TreeSet<>();
        annotations.addAll(mViewBinderCompiler.getSupportedAnnotationTypes());
        annotations.addAll(mExpandableViewBinderCompiler.getSupportedAnnotationTypes());
        annotations.addAll(mSwipeViewBinderCompiler.getSupportedAnnotationTypes());
        annotations.addAll(mSwipeDirectionalViewBinderCompiler.getSupportedAnnotationTypes());
        return annotations;

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}