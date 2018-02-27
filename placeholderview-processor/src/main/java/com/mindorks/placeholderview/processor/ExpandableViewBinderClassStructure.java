package com.mindorks.placeholderview.processor;

import com.mindorks.placeholderview.annotations.internal.BindingSuffix;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by ali on 27/02/18.
 */

public class ExpandableViewBinderClassStructure extends ViewBinderClassStructure {

    public ExpandableViewBinderClassStructure(ClassDetail classDetail) {
        super(classDetail);
    }

    public static ExpandableViewBinderClassStructure create(TypeElement typeElement, Elements elementUtils) {
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        return new ExpandableViewBinderClassStructure(new ClassDetail(
                typeElement,
                packageName,
                BindingSuffix.CLASS_EXPANDABLE_VIEW_BINDER_SUFFIX));
    }
}
