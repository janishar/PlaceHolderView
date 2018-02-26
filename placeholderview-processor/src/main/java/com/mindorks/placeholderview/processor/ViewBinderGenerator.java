package com.mindorks.placeholderview.processor;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.internal.BindingSuffix;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;

/**
 * Created by ali on 26/02/18.
 */

public class ViewBinderGenerator {

    private TypeElement typeElement;
    private String packageName;
    private String typeName;
    private ClassName className;
    private ClassName binderClassName;
    private ClassName viewBinderClassName;
    private ClassName androidViewClassName;
    private List<VariableElement> variableElements;
    private List<ExecutableElement> executableElements;

    public ViewBinderGenerator(TypeElement typeElement, String packageName) {
        this.typeElement = typeElement;
        this.packageName = packageName;
    }

    private void setup() {
        typeName = typeElement.getSimpleName().toString();
        className = ClassName.get(packageName, typeName);
        binderClassName = ClassName.get(packageName,
                typeName + BindingSuffix.CLASS_VIEW_BINDER_SUFFIX);
        viewBinderClassName = ClassName.get(NameStore.Package.PLACE_HOLDER_VIEW, NameStore.Class.VIEW_BINDER);
        androidViewClassName = ClassName.get(NameStore.Package.ANDROID_VIEW, NameStore.Class.ANDROID_VIEW);
        variableElements = ElementFilter.fieldsIn(typeElement.getEnclosedElements());
        executableElements = ElementFilter.methodsIn(typeElement.getEnclosedElements());
    }

    private ClassBuilder createClassBuilder() {
        setup();
        TypeSpec.Builder classBinder = TypeSpec.classBuilder(binderClassName)
                .superclass(ParameterizedTypeName.get(
                        viewBinderClassName,
                        TypeVariableName.get(typeElement.asType()),
                        TypeVariableName.get(androidViewClassName.simpleName())))
                .addModifiers(Modifier.PUBLIC);
        return new ClassBuilder(classBinder);
    }

    private class ClassBuilder {
        private TypeSpec.Builder classBinder;

        public ClassBuilder(TypeSpec.Builder classBinder) {
            this.classBinder = classBinder;
        }

        public ClassBuilder addConstructor() {
            Layout layout = typeElement.getAnnotation(Layout.class);
            NonReusable nonReusable = typeElement.getAnnotation(NonReusable.class);
            boolean nullable = nonReusable != null;
            classBinder.addMethod(MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PROTECTED)
                    .addParameter(className, NameStore.Variable.RESOLVER)
                    .addStatement("super($N, $L, $L)", NameStore.Variable.RESOLVER, layout.value(), nullable)
                    .build());
            return this;
        }

        public ClassBuilder addResolveViewMethod() {
            classBinder.addMethod(MethodSpec.methodBuilder(NameStore.Method.RESOLVE_VIEW)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER)
                    .build());
            return this;
        }

        public ClassBuilder addBindLongPressMethod() {
            classBinder.addMethod(MethodSpec.methodBuilder(NameStore.Method.BIND_LONG_PRESS)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(void.class)
                    .addParameter(className, NameStore.Variable.RESOLVER)
                    .addParameter(androidViewClassName, NameStore.Variable.ITEM_VIEW)
                    .build());
            return this;
        }

        public ClassBuilder addResolveViewMethod() {

            return this;
        }

        public ClassBuilder addResolveViewMethod() {

            return this;
        }

        public ClassBuilder addResolveViewMethod() {

            return this;
        }
    }

}
