---
id: terminology
title: Terminology
sidebar_label: Terminology
---
## PlaceHolderView supplies Annotations that does three functions:
1. Define configuration through annotation on the class.
2. Bind the object references to the annotated global variables.
3. Bind callbacks to the defined class methods.

## Item View Configuration
A class that serves as the item view defines the properties and actions on ballback. Some common annotations are provided for the view configuration.

Example:

```java
@NonReusable
@Layout(R.layout.gallery_item_big)
public class ItemView {
...
}
```

**`@NonReusable`**: Should be provided if we intend to remove all the references hold by the item view when it is removed. This will prevent accidental memory leaks.

**`@Layout`**: It takes a layout defined in xml file specified by `R.id.<lalyout-id>` and bind that layout with the annotated class. This item view is used to inflate the view.

## Refrencing Views in Layout
The views defined in the layout provided by `@Layout` can be referenced in the item view class using `@View` annotations on the global variable.

Example:

```java
@Layout(R.layout.gallery_item_big)
public class ItemView {

    @View(R.id.profile_image_view)
    ImageView profileImageView;

    @View(R.id.name_age_txt)
    TextView nameAgeTxt;

    @View(R.id.location_name_txt)
    TextView locationNameTxt;
...
}
```

## Binding click listeners to a view
Any view defined in the layout can be provided with `View.OnClickListener` using `@Click` annotation:

Example:
```java
@Layout(R.layout.gallery_item_big)
public class ItemView {
...
    @Click(R.id.profile_image_view)
    public void onProfileImageViewClick() {
        Log.d("DEBUG", "profileImageView clicked");
    }
...
}
```