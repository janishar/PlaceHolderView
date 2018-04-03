package com.mindorks.placeholderview.compiler.structures;

import com.mindorks.placeholderview.annotations.Keep;
import com.mindorks.placeholderview.annotations.infinite.LoadMore;
import com.mindorks.placeholderview.annotations.internal.BindingSuffix;
import com.mindorks.placeholderview.compiler.RClassBuilder;
import com.mindorks.placeholderview.compiler.core.ClassDetail;
import com.mindorks.placeholderview.compiler.core.ClassStructure;
import com.mindorks.placeholderview.compiler.core.IllegalUseException;
import com.mindorks.placeholderview.compiler.core.NameStore;
import com.mindorks.placeholderview.compiler.core.Validator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by ali on 27/02/18.
 */
public class LoadMoreViewBinderClassStructure extends ClassStructure {

    public LoadMoreViewBinderClassStructure(ClassDetail classDetail) {
        super(classDetail);
    }

    public static LoadMoreViewBinderClassStructure create(TypeElement typeElement,
                                                          Elements elementUtils,
                                                          RClassBuilder rClassBuilder) {
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        return new LoadMoreViewBinderClassStructure(new ClassDetail(
                typeElement,
                packageName,
                NameStore.Class.VIEW_BINDER,
                BindingSuffix.CLASS_VIEW_BINDER_SUFFIX));
    }

    @Override
    protected TypeSpec.Builder createClassBuilder() {
        ClassName parentClassName = getClassDetail().getGeneratedClassName();
        ClassName generatedClassName = ClassName.get(
                getClassDetail().getPackageName(),
                getClassDetail().getTypeName() +
                        BindingSuffix.CLASS_LOAD_MORE_VIEW_BINDER_SUFFIX);

        ClassName loadMoreCallbackClassName = ClassName.get(
                NameStore.Package.PLACE_HOLDER_VIEW,
                NameStore.Class.LOAD_MORE_CALLBACK_BINDER);

        return TypeSpec.classBuilder(generatedClassName)
                .superclass(parentClassName)
                .addSuperinterface(ParameterizedTypeName.get(loadMoreCallbackClassName,
                        TypeVariableName.get(getClassDetail().getTypeElement().asType())))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Keep.class);
    }

    public LoadMoreViewBinderClassStructure addConstructor() {
        getClassBuilder().addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER)
                .addStatement("super($N)", NameStore.Variable.RESOLVER)
                .build());
        return this;
    }

    public LoadMoreViewBinderClassStructure addBindLoadMoreMethod() throws IllegalUseException {
        MethodSpec.Builder bindLoadMoreMethodBuilder = MethodSpec.methodBuilder(NameStore.Method.BIND_LOAD_MORE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            LoadMore loadMore = executableElement.getAnnotation(LoadMore.class);
            if (loadMore != null) {
                Validator.validateLoadMore(executableElement);
                bindLoadMoreMethodBuilder.addStatement("$N.$N()",
                        NameStore.Variable.RESOLVER,
                        executableElement.getSimpleName());
            }
        }
        getClassBuilder().addMethod(bindLoadMoreMethodBuilder.build());
        return this;
    }
}
