package com.mindorks.placeholderview.processor;

import com.google.common.collect.ImmutableSet;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.View;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class PlaceHolderViewProcessor extends AbstractProcessor {

    private static final String CLASS_SUFFIX = "Binder";
    private static final String METHOD_PREFIX = "bind";
    private static final String LAYOUT_ID_VARIABLE_NAME = "layoutId";
    private static final String LAYOUT_VIEW_VARIABLE_NAME = "layoutView";
    private static final String NAME_SEPARATOR = "_";
    private static final ClassName classView =
            ClassName.get("android.view", "View");
    private static final ClassName classContext =
            ClassName.get("android.content", "Context");
    private static final ClassName classLayoutInflater =
            ClassName.get("android.view", "LayoutInflater");

    private Filer filer;
    private Messager messager;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(Layout.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Can be applied to class.");
                return true;
            }

            TypeElement typeElement = (TypeElement) element;
            String typeName = element.getSimpleName().toString();
            String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            ClassName className = ClassName.get(packageName, typeName);
            ClassName binderClassName = ClassName.get(packageName, typeName + CLASS_SUFFIX);

            Layout layout = element.getAnnotation(Layout.class);
            if (layout.value() <= 0) {
                messager.printMessage(Diagnostic.Kind.ERROR, "@Layout should have positive value passed");
                return true;
            }

            TypeSpec.Builder classBinder = TypeSpec
                    .classBuilder(binderClassName)
                    .addModifiers(Modifier.PUBLIC);

            FieldSpec layoutView = FieldSpec.builder(classView, LAYOUT_VIEW_VARIABLE_NAME)
                    .addModifiers(Modifier.PRIVATE)
                    .build();
            classBinder.addField(layoutView);

            MethodSpec classBinderConstructor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(classContext, "context")
                    .addStatement("$N = $T.from($N).inflate($L, $L, $L)",
                            LAYOUT_VIEW_VARIABLE_NAME,
                            classLayoutInflater,
                            "context",
                            layout.value(),
                            null,
                            false)
                    .build();
            classBinder.addMethod(classBinderConstructor);

            List<VariableElement> variableElements = ElementFilter.fieldsIn(typeElement.getEnclosedElements());
            for (VariableElement variableElement : variableElements) {
                View view = variableElement.getAnnotation(View.class);
                if (view != null) {
                    if (view.value() <= 0) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "@View should have positive value passed");
                        return true;
                    }
                    MethodSpec bindViewMethod = MethodSpec
                            .methodBuilder(METHOD_PREFIX + NAME_SEPARATOR + variableElement.getSimpleName())
                            .addModifiers(Modifier.PUBLIC)
                            .returns(void.class)
                            .addParameter(className, "resolver")
                            .addStatement("$T $N = $N$N($L)", variableElement, "view", LAYOUT_VIEW_VARIABLE_NAME, ".findViewById", view.value())
                            .addStatement("$N.$N=$N", "resolver", variableElement.getSimpleName(), "view")
                            .build();

                    classBinder.addMethod(bindViewMethod);
                }
            }

            try {
                JavaFile.builder(packageName, classBinder.build())
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * return value of this method will be given to us as process method’s first parameter.
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(
                Click.class.getCanonicalName(),
                Layout.class.getCanonicalName(),
                View.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}

