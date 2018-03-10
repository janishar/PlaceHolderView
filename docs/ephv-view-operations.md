---
id: ephv-view-operations
title: View Operations
sidebar_label: View Operations
---

## General Operations
Most of the view operations are same as the PlaceHolderView's operation. This doc can be found [here](phv-view-definitions.md).

## Deprecated Methods
Following methods should not be used as it will produce unexpected results.
1. `expandableView.addView(2, someItemView)`
2. `expandableView.addViewBefore(someItemView1, someItemView2)`
2. `expandableView.addViewAfter(someItemView1, someItemView2)`

## Add parent and child view
`expandableView.addView` automatically infer if it is parent or child. All views added after a parent view is considered as its child only if it is not a parent itself.

```java
public class ExpandableActivity extends AppCompatActivity {
...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        expandableView.addView(new HeadingView(getApplicationContext(), "Heading 1")) //parent 1
                        .addView(new InfoView(getApplicationContext(), 
                                new Info("Title1", "https://example/image1.png"))) //child 1 of parent 1
                        .addView(new InfoView(getApplicationContext(), 
                                new Info("Title2", "https://example/image2.png"))) //child 2 of parent 1
                        .addView(new HeadingView(getApplicationContext(), "Heading 2")) //parent 2
                        .addView(new InfoView(getApplicationContext(), 
                                new Info("Title1", "https://example/image1.png"))) //child 1 of parent 2
                        .addView(new InfoView(getApplicationContext(), 
                                new Info("Title2", "https://example/image2.png"))) //child 2 of parent 2
                        .addView(new InfoView(getApplicationContext(), 
                                new Info("Title2", "https://example/image3.png"))) //child 1 of parent 2
        ...
    }
}
```

**Add child view to a parent using the parent's view instance.**
```java
expandableView.addChildView(parentView, childView);
```
**Add child view to a parent using the parent's view position.**
```java
expandableView.addChildView(2, childView);
```

## Expand Programmatically
A parent can be expanded programmatically by using either its instance or position.
```java
try {
        expandableView.expand(parentItemView);
} catch (Resources.NotFoundException e) {
        e.printStackTrace();
}
```
```java
try {
        expandableView.expand(2);
} catch (Resources.NotFoundException e) {
        e.printStackTrace();
}
```

## Collapse Programmatically
A parent can be collapse programmatically by using either its instance or position.
```java
try {
        expandableView.collapse(parentItemView);
} catch (Resources.NotFoundException e) {
        e.printStackTrace();
}
```

```java
try {
        expandableView.collapse(2);
} catch (Resources.NotFoundException e) {
        e.printStackTrace();
}
```

**Collapse all the parents.**
```java
expandableView.collapseAll();
```