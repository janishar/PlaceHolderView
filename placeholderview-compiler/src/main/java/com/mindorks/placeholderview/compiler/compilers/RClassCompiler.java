package com.mindorks.placeholderview.compiler.compilers;

import com.mindorks.placeholderview.compiler.RClassBuilder;
import com.mindorks.placeholderview.compiler.core.Compiler;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by janisharali on 04/03/18.
 */

public class RClassCompiler extends Compiler {

    public RClassCompiler(Filer filer,
                          Messager messager,
                          Elements elementUtils,
                          RClassBuilder rClassBuilder) {
        super(filer, messager, elementUtils, rClassBuilder);
    }

    @Override
    public boolean compile(RoundEnvironment roundEnv) {
        try {
            JavaFile.builder(getRClassBuilder().getClassName().packageName(),
                    getRClassBuilder().build())
                    .build()
                    .writeTo(getFiler());
        } catch (IOException e) {
            // Allowing to successive rewrite file
            return true;
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new TreeSet<>();
    }
}
