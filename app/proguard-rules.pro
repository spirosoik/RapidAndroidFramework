# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:/Program Files (x86)/Android/android-studio/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
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
-ignorewarnings
-dontoptimize
-dontobfuscate
-dontskipnonpubliclibraryclasses

-ignorewarnings

-keepattributes Exceptions, Signature, InnerClasses, *Annotation*
# Allow obfuscation of android.support.v7.internal.view.menu.**
# to avoid problem on Samsung 4.2.2 devices with appcompat v21
# see https://code.google.com/p/android/issues/detail?id=78377
-keep class !android.support.v7.internal.view.menu.MenuBuilder, !android.support.v7.internal.view.menu.SubMenuBuilder, android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }


#BUTTERKNIFE
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}
###################### 

#OTTO BUS
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}
######################

#KOMENSKY
-keep class eu.inmite.android.lib.validations.form.annotations.** { *; }
-keep class * implements eu.inmite.android.lib.validations.form.iface.ICondition
-keep class * implements eu.inmite.android.lib.validations.form.iface.IValidator
-keep class * implements eu.inmite.android.lib.validations.form.iface.IFieldAdapter
-keepclassmembers class ** {
    @eu.inmite.android.lib.validations.form.annotations.** *;
}
######################

#DAGGER
-dontwarn dagger.internal.codegen.**
-keep @interface dagger.*,javax.inject.*

#Keep the Modules intact
-keep @dagger.Module class *

#-Keep the fields annotated with @Inject of any class that is not deleted.
-keepclassmembers class * {
  @javax.inject.* <fields>;
}

#-Keep the names of classes that have fields annotated with @Inject and the fields themselves.
-keepclasseswithmembernames class * {
  @javax.inject.* <fields>;
}

#Keep the generated classes by dagger-compile
-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection
######################

# OKHTTP
-dontwarn com.squareup.okhttp.**
###################### 


