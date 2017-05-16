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
-optimizationpasses 5                                                             # 指定代码的压缩级别
-dontusemixedcaseclassnames                                                      # 是否使用大小写混合
-dontskipnonpubliclibraryclasses                                                # 是否混淆第三方jar
-dontpreverify                                    # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
-dontoptimize    #表示不进行优化，建议使用此选项，因为根据proguard-android-optimize.txt中的描述，优化可能会造成一些潜在风险，不能保证在所有版本的Dalvik上都正常运行。
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法



-keepattributes *Annotation* #表示对注解中的参数进行保留。
-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆
-keep public class com.google.vending.licensing.ILicensingService
 #如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
#忽略警告
    -ignorewarning


-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclassmembers public class * extends android.view.View { #表示不混淆任何一个View中的setXxx()和getXxx()方法
   void set*(***);
   *** get*();
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class **.R$* {#表示不混淆R文件中的所有静态字段，我们都知道R文件是通过字段来记录每个资源的id的，字段名要是被混淆了，id也就找不着了。
    public static <fields>;
}

#如果有引用v4包可以添加下面这行
-keep class android.support.v4. { *; }
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService
-keep interface android.support.v7.** { *; }

#如果引用了v4或者v7包，可以忽略警告，因为用不到android.support
-dontwarn android.support.**

#保持自定义组件不被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn com.facebook.**
-keep class com.facebook.drawee.** { *; }
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

#sharesdk
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
  #7dns
  -keep class com.qiniu.android.dns.** { *; }
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
-libraryjars libs/alipaySDK-20170510.jar
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

 #高德定位
 -keep class com.amap.api.location.**{*;}
 -keep class com.amap.api.fence.**{*;}
 -keep class com.autonavi.aps.amapapi.model.**{*;}

 #友盟
 -keepclassmembers class * {
    public <init> (org.json.JSONObject);
 }
 -keep public class [com.fubang.live].R$*{
 public static final int *;
 }
 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }

 #Klog
  -keep class com.socks.**{*;}

 #liteorm
 -keep class com.litesuits.orm.**{*;}

 #mob
 -keep class com.mob.commons.**{*;}
 -keep class com.mob.tools.**{*;}

 #picasso
 -keep class com.squareup.picasso.**{*;}
 -keep class com.squareup.javawriter.**{*;}

 #utdid4all
 -keep class com.ta.utdid2.**{*;}
 -keep class com.ut.device.**{*;}

 #baseRecycleView
 -keep class com.chad.library.**{*;}
 #butterknife
 -keep class butterknife.**{*;}
 #bolts
 -keep class bolts.**{*;}
 #circleImageView
 -keep class de.hdodenhof.circleimageview.**{*;}
 #converter-gson
 -keep class retrofit2.converter.gson.**{*;}
 #hamcrest
 -keep class org.** { *; }
 #nineold
  -keep class com.nineoldandroids.**{*;}
 #permission
  -keep class kr.co.namee.permissiongen.**{*;}
 #rxandroid java
  -keep class rx.**{*;}
 #takephoto
  -keep class com.jph.takephoto.**{*;}
 #verticalvp
  -keep class me.kaelaela.verticalviewpager.**{*;}
 #zxing
  -keep class com.uuzuche.lib_zxing.**{*;}
 #entity类不被混淆
  -keep class com.fubang.live.entities.**{*;}
#okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}


#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}


#okio
-dontwarn okio.**
-keep class okio.**{*;}

