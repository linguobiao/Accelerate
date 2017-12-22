# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

#-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
#-dontoptimize
-dontpreverify
# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
                                       # instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.

#-ignorewarnings

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void openFileChooser(...);
    public ** onShowFileChooser(...);
}

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-keep class android.support.**{*;}
-dontwarn android.support.**
-keep class com.android.org.conscrypt.**{*;}
-keep class alvik.system.**{*;}
-keep class sun.security.ssl.**{*;}

##---------------Begin: proguard configuration for Gson  ----------
-keepattributes Signature
-keep class sun.misc.** { *; }
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }
-keepclassmembers class ** {
    @com.yanzhenjie.permission.PermissionYes <methods>;
}
-keepclassmembers class ** {
    @com.yanzhenjie.permission.PermissionNo <methods>;
}

##---------------Begin: proguard configuration for Gson ----------
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}

-keepattributes Signature
-keepattributes *Annotation*
-keep class com.lgb.accelerate.Global.Constant.** { *; }
-keep class com.lgb.accelerate.Global.Constant { *; }
-keep class com.lgb.accelerate.bean.** { *; }
##---------------End: proguard configuration for Gson ----------


#okhttp
-keep  interface okio.**
-keep  class okio.**
-keep  class okio.** {*;}
-keep enum  okio.**

-keep  interface okhttp3.**
-keep  class okhttp3.**
-keep  class okhttp3.** {*;}
-keep enum  okhttp3.**

-dontwarn okio.**
-dontwarn okhttp3.**

-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

-keep  interface org.apache.**
-keep  class org.apache.**
-keep  class org.apache.** {*;}

-dontwarn org.apache.**
-dontnote android.net.http.*
-dontnote org.apache.**

-dontwarn java.awt.**
-dontwarn javax.**



-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String,int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

#--------------------------------HermesEventBus 混淆--------------------------------------
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keep class org.xclcharts.** {*;}
