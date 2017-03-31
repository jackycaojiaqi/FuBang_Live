# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/jacky/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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
-keep public class * extends android.app.Activity                         ->所有activity的子类不要去混淆
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-dontwarn android.support.v4.**
-dontwarn android.annotation


-libraryjars libs/android-support-v4.jar


-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keep class android.support.v4.**{*;}
-keepattributes *Annotation*

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn com.facebook.**
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
#annotation 混淆
-dontwarn org.springframework.**

-dontwarn cn.sharesdk.**
-keep class cn.sharesdk.** { *; }

-dontwarn com.handmark.pulltorefresh.library.**
-keep class com.handmark.pulltorefresh.library.** { *;}
-dontwarn com.handmark.pulltorefresh.library.extras.**
-keep class com.handmark.pulltorefresh.library.extras.** { *;}
-dontwarn com.handmark.pulltorefresh.library.internal.**
-keep class com.handmark.pulltorefresh.library.internal.** { *;}

-dontwarn com.example.**
-keep class com.example.** {*;}

-dontwarn com.zhuyunjian.libraty.**
-keep class com.zhuyunjian.libraty.** {*;}

-dontwarn com.loveplusplus.update.**
-keep class com.loveplusplus.update.** {*;}

##   ########## Gson混淆    ##########

## ----------------------------------

-keepattributes Signature

-keep class sun.misc.Unsafe { *; }

-keep class com.google.gson.examples.android.model.** { *; }



# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }
 #7牛推流
 -keep class com.qiniu.pili.droid.streaming.** { *; }
 #七牛播放端
 -keep class com.pili.pldroid.player.** { *; }
 -keep class tv.danmaku.ijk.media.player.** {*;}
#eventbus
-keep class org.simple.** { *; }
-keep interface org.simple.** { *; }
-keepclassmembers class * {
    @org.simple.eventbus.Subscriber <methods>;
}
-keepattributes *Annotation*

# # 支付宝支付混淆
-libraryjars libs/alipaySDK-20150602.jar

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}