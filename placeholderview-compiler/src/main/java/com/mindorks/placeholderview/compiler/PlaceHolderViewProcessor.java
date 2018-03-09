package com.mindorks.placeholderview.compiler;

import com.mindorks.placeholderview.compiler.compilers.ExpandableViewBinderCompiler;
import com.mindorks.placeholderview.compiler.compilers.LoadMoreViewBinderCompiler;
import com.mindorks.placeholderview.compiler.compilers.RClassCompiler;
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

    private ViewBinderCompiler viewBinderCompiler;
    private ExpandableViewBinderCompiler expandableViewBinderCompiler;
    private SwipeViewBinderCompiler swipeViewBinderCompiler;
    private SwipeDirectionalViewBinderCompiler swipeDirectionalViewBinderCompiler;
    private LoadMoreViewBinderCompiler loadMoreViewBinderCompiler;
    private RClassCompiler rClassCompiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        Filer filer = processingEnv.getFiler();
        Messager messager = processingEnv.getMessager();
        Elements elementUtils = processingEnv.getElementUtils();

        RClassBuilder rClassBuilder = RClassBuilder.create(filer, messager);

        rClassCompiler = new RClassCompiler(
                filer, messager, elementUtils, rClassBuilder);
        viewBinderCompiler = new ViewBinderCompiler(
                filer, messager, elementUtils, rClassBuilder);
        expandableViewBinderCompiler = new ExpandableViewBinderCompiler(
                filer, messager, elementUtils, rClassBuilder);
        swipeViewBinderCompiler = new SwipeViewBinderCompiler(
                filer, messager, elementUtils, rClassBuilder);
        swipeDirectionalViewBinderCompiler = new SwipeDirectionalViewBinderCompiler(
                filer, messager, elementUtils, rClassBuilder);
        loadMoreViewBinderCompiler = new LoadMoreViewBinderCompiler(
                filer, messager, elementUtils, rClassBuilder);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return viewBinderCompiler.compile(roundEnv)
                && expandableViewBinderCompiler.compile(roundEnv)
                && swipeViewBinderCompiler.compile(roundEnv)
                && swipeDirectionalViewBinderCompiler.compile(roundEnv)
                && loadMoreViewBinderCompiler.compile(roundEnv)
                && rClassCompiler.compile(roundEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new TreeSet<>();
        annotations.addAll(viewBinderCompiler.getSupportedAnnotationTypes());
        annotations.addAll(expandableViewBinderCompiler.getSupportedAnnotationTypes());
        annotations.addAll(swipeViewBinderCompiler.getSupportedAnnotationTypes());
        annotations.addAll(swipeDirectionalViewBinderCompiler.getSupportedAnnotationTypes());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}