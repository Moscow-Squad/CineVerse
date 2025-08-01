# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Firebase
#-keep class com.google.firebase.** { *; }
#-dontwarn com.google.firebase.**
#
## TensorFlow Lite (if used)
#-keep class org.tensorflow.lite.** { *; }
#-dontwarn org.tensorflow.lite.**
#
## Keep annotations and classes used by Hilt
#-keep class dagger.hilt.** { *; }
#-keep class androidx.hilt.** { *; }
#
## Prevent R8 from removing unused classes in ML models
#-keep class com.google.mlkit.** { *; }
#-dontwarn com.google.mlkit.**
#
## Optimize aggressively
#-assumenosideeffects class android.util.Log {
#    public static *** d(...);
#    public static *** v(...);
#    public static *** i(...);
#}
# Keep Composables
-keep class * {
    @androidx.compose.runtime.Composable <methods>;
}

# If using Navigation Compose:
-keep class androidx.navigation.** { *; }
-keepclassmembers class * {
    @androidx.navigation.* <methods>;
}

# If using Hilt/Koin
-keep class * {
    @dagger.* <methods>;
    @org.koin.* <methods>;
}
