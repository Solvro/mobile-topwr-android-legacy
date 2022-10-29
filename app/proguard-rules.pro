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

-keep class com.solvro.topwr.data.model.maps.* { *; }
-keep class com.solvro.topwr.data.model.aboutUs.* { *; }
-keep class com.solvro.topwr.data.model.building.* { *; }
-keep class com.solvro.topwr.data.model.common.* { *; }
-keep class com.solvro.topwr.core.api.model.department.* { *; }
-keep class com.solvro.topwr.core.api.model.departments.* { *; }
-keep class com.solvro.topwr.data.model.endDate.* { *; }
-keep class com.solvro.topwr.data.model.date.* { *; }
-keep class com.solvro.topwr.data.model.info.* { *; }
-keep class com.solvro.topwr.data.model.notices.* { *; }
-keep class com.solvro.topwr.data.model.tag.* { *; }
-keep class com.solvro.topwr.data.model.scienceClub.* { *; }