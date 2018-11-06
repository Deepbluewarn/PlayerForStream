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

 -assumenosideeffects class android.util.Log {
     public static boolean isLoggable(java.lang.String, int);
     public static int v(...);
     public static int i(...);
     public static int w(...);
     public static int d(...);
     public static int e(...);
 }

 -dontwarn android.support.**
 ###########################################################################
 -keep public class * implements com.bumptech.glide.module.GlideModule
 -keep public class * extends com.bumptech.glide.module.AppGlideModule
 -keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
   **[] $VALUES;
   public *;
 }
 ############################################################################
 # Begin: Proguard rules for retrofit2

 # Platform calls Class.forName on types which do not exist on Android to determine platform.
 -dontnote retrofit2.Platform
 # Platform used when running on RoboVM on iOS. Will not be used at runtime.
 -dontnote retrofit2.Platform$IOS$MainThreadExecutor
 # Platform used when running on Java 8 VMs. Will not be used at runtime.
 -dontwarn retrofit2.Platform$Java8
 # Retain generic type information for use by reflection by converters and adapters.
 -keepattributes Signature
 # Retain declared checked exceptions for use by a Proxy instance.
 -keepattributes Exceptions

-keepclasseswithmembers class * {
      @retrofit2.http.* <methods>;
  }

  # Retrofit
  -dontwarn retrofit2.**
  -dontwarn org.codehaus.mojo.**
  -keep class retrofit2.** { *; }
  -keepattributes Signature
  -keepattributes Exceptions
  -keepattributes *Annotation*

  -keepattributes RuntimeVisibleAnnotations
  -keepattributes RuntimeInvisibleAnnotations
  -keepattributes RuntimeVisibleParameterAnnotations
  -keepattributes RuntimeInvisibleParameterAnnotations

  -keepattributes EnclosingMethod

  -keepclasseswithmembers class * {
      @retrofit2.* <methods>;
  }

  -keepclasseswithmembers interface * {
      @retrofit2.* <methods>;
  }
 # End: Proguard rules for retrofit2

 # Gson uses generic type information stored in a class file when working with fields. Proguard
 # removes such information by default, so configure it to keep all of it.
 -keepattributes Signature

 ############################################################################
 # For using GSON @Expose annotation
 -keepattributes *Annotation*

 # Gson specific classes
 -keep class sun.misc.Unsafe { *; }
 #-keep class com.google.gson.stream.** { *; }

 # Application classes that will be serialized/deserialized over Gson
 -keep class com.google.gson.examples.android.model.** { *; }

 # Prevent proguard from stripping interface information from TypeAdapterFactory,
 # JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
 -keep class * implements com.google.gson.TypeAdapterFactory
 -keep class * implements com.google.gson.JsonSerializer
 -keep class * implements com.google.gson.JsonDeserializer
 ############################################################################
 -dontwarn okhttp3.**
 -dontwarn okio.**

 -dontnote okhttp3.**
 #####################################

 #######################################
 ######################################
-dontwarn lombok.**
-dontwarn org.slf4j.**


-keep class ch.qos.** { *; }
-keep class org.slf4j.** { *; }
-keepattributes *Annotation*
# If you don't use the mailing features of logback (i.e., the SMTPAppender), you might encounter an error while exporting your app with ProGuard. To resolve this, add the following rule:
-dontwarn ch.qos.logback.core.net.*

#-libraryjars libs/pircbotx-2.2-20180822.104627-38.jar
-dontwarn org.pircbotx.**
#-dontwarn com.google.common.util.concurrent.**
#-dontwarn com.google.common.primitives.**
-dontwarn com.google.**
#-dontwarn afu.org.checkerframework.checker.**
#-dontwarn org.checkerframework.checker.**


#for Guava


# Guava 20.0
#-dontwarn afu.org.checkerframework.**
#-dontwarn org.checkerframework.**

-keep class com.google.common.io.Resources {
public static *;
}
-keep class com.google.common.collect.Lists {
public static ** reverse(**);
}
-keep class com.google.common.base.Charsets {
public static *;
}

-keep class com.google.common.base.Joiner {
public static com.google.common.base.Joiner on(java.lang.String);
public ** join(...);
}

-keep class com.google.common.collect.MapMakerInternalMap$ReferenceEntry
-keep class com.google.common.cache.LocalCache$ReferenceEntry

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe

-dontwarn java.lang.ClassValue
-dontwarn com.google.j2objc.annotations.Weak
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-dontwarn afu.org.checkerframework.**
-dontwarn org.checkerframework.**
-dontwarn com.google.errorprone.annotations.CanIgnoreReturnValue
-dontwarn com.google.errorprone.annotations.concurrent.LazyInit
-dontwarn com.google.errorprone.annotations.ForOverride
-dontwarn com.google.common.collect.MinMaxPriorityQueue
-dontwarn com.google.common.util.concurrent.FuturesGetChecked**
-dontwarn javax.lang.model.element.Modifier
#end Guava

#Start UserHabit
-dontwarn android.support.**
-dontwarn org.apache.http.**
-dontwarn android.webkit.WebViewClient
-keepnames interface android.support.v4.** { *; }
-keepnames class android.support.v4.** { *; }
-keepnames class android.support.v7.** { *; }
-keepnames interface android.support.v7.** { *; }
-keep class org.apache.** { *; }
-keep interface org.apache.http.** { *; }
#End UserHabit

-keepclassmembers class name.cpr.VideoEnabledWebView$JavascriptInterface { public *; }
