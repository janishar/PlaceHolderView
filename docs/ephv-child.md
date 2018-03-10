---
id: ephv-child
title: Child View Definition
sidebar_label: Child View Definition
---

## View Definition
An item view to be used as a child view we don't have to do anything different from that of ordinary PlaceHoldeView.

PlaceHoldeView docs can be found [here](terminology.md)

Example:
```java
@Layout(R.layout.feed_item)
public class InfoView {

    @ParentPosition
    int parentPosition;

    @ChildPosition
    int childPosition;

    @View(R.id.titleTxt)
    TextView titleTxt;

    @View(R.id.imageView)
    ImageView imageView;

    Info info;
    Context context;

    public InfoView(Context context, Info info) {
        this.context = context;
        this.info = info;
    }

    @Resolve
    public void onResolved() {
        titleTxt.setText(mInfo.getTitle());
        Glide.with(mContext).load(mInfo.getImageUrl()).into(imageView);
    }
}
``` 

## Getting positions
A child view can know its parent position using `@ParentPosition` and it's own relative postion using `@ChildPosition` annotations. 

> `@Position` will give absolute position and its not meaningful in this context. 