-dontoptimize
-dontusemixedcaseclassnames
-dontpreverify
-dontskipnonpubliclibraryclasses
-verbose

# Keep debug info
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-renamesourcefileattribute SourceFile

# Kotlin serialization
-keepattributes InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.jaychang.food.**$$serializer { *; }
-keepclassmembers class com.jaychang.food.** {
    *** Companion;
}
-keepclasseswithmembers class com.jaychang.food.** {
    kotlinx.serialization.KSerializer serializer(...);
}