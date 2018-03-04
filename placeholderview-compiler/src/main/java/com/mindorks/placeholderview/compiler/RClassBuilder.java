package com.mindorks.placeholderview.compiler;

import com.mindorks.placeholderview.compiler.core.NameStore;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Created by janisharali on 04/03/18.
 */

public final class RClassBuilder {

    private Filer filer;
    private Messager messager;
    private HashMap<String, Integer> layoutNameAndIdMap = new LinkedHashMap<>();
    private HashMap<String, Integer> viewNameAndIdMap = new LinkedHashMap<>();
    private TypeSpec.Builder classBuilder;
    private TypeSpec.Builder layoutClassBuilder;
    private TypeSpec.Builder idClassBuilder;
    private ClassName className;
    private ClassName layoutClassName;
    private ClassName idClassName;

    private RClassBuilder(Filer filer,
                          Messager messager,
                          TypeSpec.Builder classBuilder,
                          TypeSpec.Builder layoutClassBuilder,
                          TypeSpec.Builder idClassBuilder,
                          ClassName className,
                          ClassName layoutClassName,
                          ClassName idClassName,
                          HashMap<String, Integer> layoutNameAndIdMap,
                          HashMap<String, Integer> viewNameAndIdMap) {
        this.filer = filer;
        this.messager = messager;
        this.classBuilder = classBuilder;
        this.layoutClassBuilder = layoutClassBuilder;
        this.className = className;
        this.idClassBuilder = idClassBuilder;
        this.layoutClassName = layoutClassName;
        this.idClassName = idClassName;
        this.layoutNameAndIdMap = layoutNameAndIdMap;
        this.viewNameAndIdMap = viewNameAndIdMap;
    }

    public static RClassBuilder create(Filer filer, Messager messager) {
        ClassName className = ClassName.get(NameStore.Package.R, NameStore.Class.R);
        return new RClassBuilder(
                filer,
                messager,
                TypeSpec.classBuilder(className)
                        .addModifiers(Modifier.PUBLIC)
                        .addModifiers(Modifier.FINAL)
                        .addMethod(MethodSpec.constructorBuilder()
                                .addModifiers(Modifier.PRIVATE)
                                .addComment("$S", "Not to be instantiated in public")
                                .build()),
                TypeSpec.classBuilder(NameStore.Class.LAYOUT)
                        .addModifiers(Modifier.PUBLIC)
                        .addModifiers(Modifier.STATIC)
                        .addModifiers(Modifier.FINAL)
                        .addMethod(MethodSpec.constructorBuilder()
                                .addModifiers(Modifier.PRIVATE)
                                .addComment("$S", "Not to be instantiated in public")
                                .build()),
                TypeSpec.classBuilder(NameStore.Class.ID)
                        .addModifiers(Modifier.PUBLIC)
                        .addModifiers(Modifier.STATIC)
                        .addModifiers(Modifier.FINAL)
                        .addMethod(MethodSpec.constructorBuilder()
                                .addModifiers(Modifier.PRIVATE)
                                .addComment("$S", "Not to be instantiated in public")
                                .build()),
                className,
                ClassName.get(NameStore.Package.R, NameStore.Class.R, NameStore.Class.LAYOUT),
                ClassName.get(NameStore.Package.R, NameStore.Class.R, NameStore.Class.ID),
                new HashMap<String, Integer>(),
                new HashMap<String, Integer>());
    }

    public String addLayoutId(TypeElement element, int layoutId) {
        int count = 0;
        String variable = element.getSimpleName().toString();
        while (layoutNameAndIdMap.containsKey(variable)) {
            // same variable and same id
            if (layoutNameAndIdMap.get(variable) == layoutId) {
                return variable;
            }
            // same variable and different ids
            variable += "_" + ++count;
        }
        /**
         * NOTE: different variables and same id and
         * different variables and different ids creates the different mapping
         */
        layoutClassBuilder.addField(FieldSpec.builder(int.class, variable)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addModifiers(Modifier.FINAL)
                .initializer("$L", layoutId)
                .build());
        layoutNameAndIdMap.put(variable, layoutId);
        return variable;
    }

    public String addViewId(Element element, int viewId) {
        int count = 0;
        String variable = element.getSimpleName().toString();
        while (viewNameAndIdMap.containsKey(variable)) {
            // same variable and same id
            if (viewNameAndIdMap.get(variable) == viewId) {
                return variable;
            }
            // same variable and different ids
            variable += "_" + ++count;
        }
        /**
         * NOTE: different variables and same id and
         * different variables and different ids creates the different mapping
         */

        idClassBuilder.addField(FieldSpec.builder(int.class, variable)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addModifiers(Modifier.FINAL)
                .initializer("$L", viewId)
                .build());
        viewNameAndIdMap.put(variable, viewId);
        return variable;
    }

    public TypeSpec build() {
        return classBuilder
                .addType(idClassBuilder.build())
                .addType(layoutClassBuilder.build())
                .build();
    }

    public ClassName getClassName() {
        return className;
    }

    public ClassName getLayoutClassName() {
        return layoutClassName;
    }

    public ClassName getIdClassName() {
        return idClassName;
    }
}
