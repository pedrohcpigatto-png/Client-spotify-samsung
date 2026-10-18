# ProGuard / R8 rules for Galaxy Music (Spotify Client)

# Preserve data models
-keepclassmembers class com.example.model.** { *; }
-keep class com.example.model.** { *; }

# Retrofit & OkHttp security rules
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Moshi
-keepclassmembers class * {
    @com.squareup.moshi.* <fields>;
}

# Preserve line numbers for stack traces
-keepattributes SourceFile,LineNumberTable
