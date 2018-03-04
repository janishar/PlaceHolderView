package com.mindorks.placeholderview.compiler.core;

import com.mindorks.placeholderview.compiler.RClassBuilder;

import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;

public abstract class Compiler {

    private Filer filer;
    private Messager messager;
    private Elements elementUtils;
    private RClassBuilder rClassBuilder;

    public Compiler(Filer filer,
                    Messager messager,
                    Elements elementUtils,
                    RClassBuilder rClassBuilder) {
        this.filer = filer;
        this.messager = messager;
        this.elementUtils = elementUtils;
        this.rClassBuilder = rClassBuilder;
    }

    public abstract boolean compile(RoundEnvironment roundEnv);

    public abstract Set<String> getSupportedAnnotationTypes();

    public Filer getFiler() {
        return filer;
    }

    public Messager getMessager() {
        return messager;
    }

    public Elements getElementUtils() {
        return elementUtils;
    }

    public RClassBuilder getRClassBuilder() {
        return rClassBuilder;
    }
}

