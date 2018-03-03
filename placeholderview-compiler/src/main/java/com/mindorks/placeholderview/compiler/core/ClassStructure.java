package com.mindorks.placeholderview.compiler.core;

import com.squareup.javapoet.TypeSpec;

/**
 * Created by ali on 27/02/18.
 */
public abstract class ClassStructure {
    private ClassDetail classDetail;
    private TypeSpec.Builder classBuilder;

    protected ClassStructure(ClassDetail classDetail) {
        this.classDetail = classDetail;
        classBuilder = createClassBuilder();
    }

    protected abstract TypeSpec.Builder createClassBuilder();

    public Generator prepare() {
        return new Generator(this);
    }

    public ClassDetail getClassDetail() {
        return classDetail;
    }

    public TypeSpec.Builder getClassBuilder() {
        return classBuilder;
    }
}
