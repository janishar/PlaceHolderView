---
id: migration
title: Migration from 1.x to 2.x
sidebar_label: Migration from 1.x to 2.x
---

## Migration to new version is very simple

* Change the gradle dependency:

**Earlier**
```groovy
dependencies {
    compile 'com.mindorks:placeholderview:0.7.3'
}
```

**Now**
```groovy
dependencies {
    ...
    compile 'com.mindorks.android:placeholderview:1.0.0'
    annotationProcessor 'com.mindorks.android:placeholderview-compiler:1.0.0'
}
```

* Annotation on private variable/method/class is not supported

1. ~~private~~
2. public
3. protected
4. local

**Recommended changes**
1. Change the private variables to local variable.
2. Convert the private methods to public or protected methods.

Example
```java
@View(R.id.headingTxt)
private TextView headingTxt;
// change to
@View(R.id.headingTxt)
TextView headingTxt;
```

```java
@Resolve
private void onResolved() {
    // something inside
}
// change to
@Resolve
public void onResolved() {
    // something inside
}
```
* Inner classes are not supported for view classes

* `Animation.<Type>` is replaced with `Animate.<Type>`<br/>
Example: `Animation.ENTER_LEFT_DESC` -> `Animate.ENTER_LEFT_DESC`

* Proguard rule is not required now.

**Remove below proguard rule if added**
```groovy
  -keepattributes *Annotation*
  -keepclassmembers class ** {
    @com.mindorks.placeholderview.annotations.** <methods>;
  }
```