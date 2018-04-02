package com.mindorks.placeholderview.compiler.structures;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.mindorks.placeholderview.annotations.expand.Toggle;
import com.mindorks.placeholderview.annotations.internal.BindingSuffix;
import com.mindorks.placeholderview.compiler.RClassBuilder;
import com.mindorks.placeholderview.compiler.core.ClassDetail;
import com.mindorks.placeholderview.compiler.core.IllegalUseException;
import com.mindorks.placeholderview.compiler.core.NameStore;
import com.mindorks.placeholderview.compiler.core.Validator;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by ali on 27/02/18.
 */

public class ExpandableViewBinderClassStructure extends ViewBinderClassStructure {

    public ExpandableViewBinderClassStructure(ClassDetail classDetail, RClassBuilder rClassBuilder) {
        super(classDetail, rClassBuilder);
    }

    public static ExpandableViewBinderClassStructure create(TypeElement typeElement,
                                                            Elements elementUtils,
                                                            RClassBuilder rClassBuilder) {
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        return new ExpandableViewBinderClassStructure(new ClassDetail(
                typeElement,
                packageName,
                NameStore.Class.EXPANDABLE_VIEW_BINDER,
                BindingSuffix.CLASS_EXPANDABLE_VIEW_BINDER_SUFFIX), rClassBuilder);
    }

    @Override
    public ExpandableViewBinderClassStructure addResolveViewMethod() throws IllegalUseException {
        return (ExpandableViewBinderClassStructure) super.addResolveViewMethod();
    }

    @Override
    public ExpandableViewBinderClassStructure addRecycleViewMethod() throws IllegalUseException {
        return (ExpandableViewBinderClassStructure) super.addRecycleViewMethod();
    }

    @Override
    public ExpandableViewBinderClassStructure addBindViewPositionMethod() throws IllegalUseException {
        return (ExpandableViewBinderClassStructure) super.addBindViewPositionMethod();
    }

    @Override
    public ExpandableViewBinderClassStructure addBindViewMethod() throws IllegalUseException {
        return (ExpandableViewBinderClassStructure) super.addBindViewMethod();
    }

    @Override
    public ExpandableViewBinderClassStructure addBindClickMethod() throws IllegalUseException {
        return (ExpandableViewBinderClassStructure) super.addBindClickMethod();
    }

    @Override
    public ExpandableViewBinderClassStructure addBindLongClickMethod() throws IllegalUseException {
        return (ExpandableViewBinderClassStructure) super.addBindLongClickMethod();
    }

    @Override
    public ExpandableViewBinderClassStructure addUnbindMethod() {
        getClassBuilder().addMethod(MethodSpec.methodBuilder(NameStore.Method.UNBIND)
                .addAnnotation(Deprecated.class)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .build());
        return this;
    }

    public ExpandableViewBinderClassStructure addBindAnimationMethod() {
        getClassBuilder().addMethod(MethodSpec.methodBuilder(NameStore.Method.BIND_ANIMATION)
                .addAnnotation(Deprecated.class)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addParameter(int.class, "deviceWidth")
                .addParameter(int.class, "deviceHeight")
                .addParameter(getClassDetail().getAndroidViewClassName(), NameStore.Variable.ANDROID_VIEW)
                .build());
        return this;
    }

    @Override
    public ExpandableViewBinderClassStructure addConstructor() {
        Layout layout = getClassDetail().getTypeElement().getAnnotation(Layout.class);
        NonReusable nonReusable = getClassDetail().getTypeElement().getAnnotation(NonReusable.class);
        Parent parentAnnotation = getClassDetail().getTypeElement().getAnnotation(Parent.class);
        SingleTop singleTopAnnotation = getClassDetail().getTypeElement().getAnnotation(SingleTop.class);
        boolean nullable = nonReusable != null;
        boolean parent = parentAnnotation != null && parentAnnotation.value();
        boolean singleTop = singleTopAnnotation != null && singleTopAnnotation.value();
        getClassBuilder()
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER)
                        .addStatement("super($N, $T.$L, $L, $L, $L)",
                                NameStore.Variable.RESOLVER,
                                getRClassBuilder().getLayoutClassName(),
                                getRClassBuilder().addLayoutId(getClassDetail().getTypeElement(), layout.value()),
                                nullable,
                                parent,
                                singleTop)
                        .build());
        return this;
    }

    public ExpandableViewBinderClassStructure addBindParentPositionMethod() throws IllegalUseException {
        MethodSpec.Builder bindParentPositionMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_PARENT_POSITION)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(int.class, NameStore.Variable.POSITION);

        for (VariableElement variableElement : getClassDetail().getVariableElements()) {
            ParentPosition position = variableElement.getAnnotation(ParentPosition.class);
            if (position != null) {
                Validator.validatePosition(variableElement);
                bindParentPositionMethodBuilder
                        .addStatement("$N().$N = $L",
                                NameStore.Method.GET_RESOLVER,
                                variableElement.getSimpleName(),
                                NameStore.Variable.POSITION)
                        .addStatement("$N($L)",
                                NameStore.Method.SET_PARENT_POSITION,
                                NameStore.Variable.POSITION);
            }
        }
        getClassBuilder().addMethod(bindParentPositionMethodBuilder.build());
        return this;
    }

    public ExpandableViewBinderClassStructure addBindChildPositionMethod() throws IllegalUseException {
        MethodSpec.Builder bindChildPositionMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_CHILD_POSITION)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(int.class, NameStore.Variable.POSITION);

        for (VariableElement variableElement : getClassDetail().getVariableElements()) {
            ChildPosition position = variableElement.getAnnotation(ChildPosition.class);
            if (position != null) {
                Validator.validatePosition(variableElement);
                bindChildPositionMethodBuilder
                        .addStatement("$N().$N = $L",
                                NameStore.Method.GET_RESOLVER,
                                variableElement.getSimpleName(),
                                NameStore.Variable.POSITION)
                        .addStatement("$N($L)",
                                NameStore.Method.SET_CHILD_POSITION,
                                NameStore.Variable.POSITION);
            }
        }
        getClassBuilder().addMethod(bindChildPositionMethodBuilder.build());
        return this;
    }

    public ExpandableViewBinderClassStructure addBindToggleMethod() throws IllegalUseException {
        MethodSpec.Builder bindToggleMethodBuilder = MethodSpec
                .methodBuilder(NameStore.Method.BIND_TOGGLE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER, Modifier.FINAL)
                .addParameter(getClassDetail().getAndroidViewClassName(), NameStore.Variable.ITEM_VIEW)
                .returns(void.class);

        TypeSpec OnClickListenerClass = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(getClassDetail().getAndroidOnClickListenerClassName())
                .addMethod(MethodSpec.methodBuilder(NameStore.Method.ANDROID_VIEW_ON_CLICK)
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Override.class)
                        .addParameter(getClassDetail().getAndroidViewClassName(), NameStore.Variable.ANDROID_VIEW)
                        .addStatement("if ($N()) $N()", NameStore.Method.IS_EXPANDED, NameStore.Method.COLLAPSE)
                        .addStatement("else $N()", NameStore.Method.EXPAND)
                        .returns(void.class)
                        .build())
                .build();

        int count = 0;
        for (VariableElement variableElement : getClassDetail().getVariableElements()) {
            Toggle toggle = variableElement.getAnnotation(Toggle.class);
            if (toggle != null) {
                if (count++ > 0) {
                    throw new IllegalUseException("@Toggle can be used only on one view");
                }
                Validator.validateToggle(variableElement, toggle);
                bindToggleMethodBuilder
                        .addStatement("$N.findViewById($T.$L).setOnClickListener($L)",
                                NameStore.Variable.ITEM_VIEW,
                                getRClassBuilder().getIdClassName(),
                                getRClassBuilder().addViewId(variableElement, toggle.value()),
                                OnClickListenerClass);
            }
        }
        if (count == 0) {
            bindToggleMethodBuilder
                    .addStatement("$N.setOnClickListener($L)",
                            NameStore.Variable.ITEM_VIEW,
                            OnClickListenerClass);
        }
        getClassBuilder().addMethod(bindToggleMethodBuilder.build());
        return this;
    }

    public ExpandableViewBinderClassStructure addBindExpandMethod() throws IllegalUseException {
        MethodSpec.Builder bindExpandMethodBuilder = MethodSpec.methodBuilder(NameStore.Method.BIND_EXPAND)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            Expand expand = executableElement.getAnnotation(Expand.class);
            if (expand != null) {
                Validator.validateExpand(executableElement);
                bindExpandMethodBuilder.addStatement("$N.$N()",
                        NameStore.Variable.RESOLVER,
                        executableElement.getSimpleName());
            }
        }
        getClassBuilder().addMethod(bindExpandMethodBuilder.build());
        return this;
    }

    public ExpandableViewBinderClassStructure addBindCollapseMethod() throws IllegalUseException {
        MethodSpec.Builder bindCollapseMethodBuilder = MethodSpec.methodBuilder(NameStore.Method.BIND_COLLAPSE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addParameter(getClassDetail().getClassName(), NameStore.Variable.RESOLVER);

        for (ExecutableElement executableElement : getClassDetail().getExecutableElements()) {
            Collapse collapse = executableElement.getAnnotation(Collapse.class);
            if (collapse != null) {
                Validator.validateCollapse(executableElement);
                bindCollapseMethodBuilder.addStatement("$N.$N()",
                        NameStore.Variable.RESOLVER,
                        executableElement.getSimpleName());
            }
        }
        getClassBuilder().addMethod(bindCollapseMethodBuilder.build());
        return this;
    }
}
