package com.mindorks.placeholderview.compiler.core;

import com.squareup.javapoet.JavaFile;

import java.io.IOException;

import javax.annotation.processing.Filer;

/**
 * Created by ali on 26/02/18.
 */

public class Generator {

    private ClassStructure classStructure;

    public Generator(ClassStructure classStructure) {
        this.classStructure = classStructure;
    }

    public void generate(Filer filer) throws IOException {
        JavaFile.builder(classStructure.getClassDetail().getPackageName(),
                classStructure.getClassBuilder().build())
                .build()
                .writeTo(filer);
    }
}