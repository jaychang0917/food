dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "build-logic"
include("dependencies")
include("linting")
include("test")
include("library")
include("feature")
include("application")


// todo can't make it work on precompiled plugin: https://github.com/gradle/gradle/issues/15383
/*
enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.5.21")
            version("lifecycle", "2.3.1")
            version("dagger", "2.37")
            version("retrofit", "2.9.0")
            version("rxbinding", "4.0.0")
            version("mockk", "1.11.0")
            version("espresso", "3.1.0")
            version("detekt", "1.18.1")
            version("coil", "1.2.2")

            // Core
            alias("kotlin-stdlib").to("org.jetbrains.kotlin","kotlin-stdlib").versionRef("kotlin")
            alias("androidx-core").to("androidx.core:core-ktx:1.3.2")
            alias("androidx-appcompat").to("androidx.appcompat:appcompat:1.3.2")
            alias("androidx-fragment").to("androidx.fragment:fragment-ktx:1.3.4")
            alias("androidx-security-crypto").to("androidx.security:security-crypto-ktx:1.1.0-alpha03")
            alias("androidx-lifecycle-runtime").to("androidx.lifecycle", "lifecycle-runtime-ktx").versionRef("lifecycle")
            alias("androidx-lifecycle-process").to("androidx.lifecycle", "lifecycle-process").versionRef("lifecycle")
            alias("androidx-lifecycle-common").to("androidx.lifecycle", "lifecycle-common-java8").versionRef("lifecycle")
            alias("androidx-lifecycle-viewmodel").to("androidx.lifecycle", "lifecycle-viewmodel-ktx").versionRef("lifecycle")
            alias("androidx-lifecycle-livedata").to("androidx.lifecycle", "lifecycle-livedata-ktx").versionRef("lifecycle")
            alias("androidx-annotation").to("androidx.annotation:annotation:1.2.0")
            alias("dagger").to("com.google.dagger", "dagger").versionRef("dagger")
            alias("dagger-compiler").to("com.google.dagger", "dagger-compiler").versionRef("dagger")
            alias("hilt-android").to("com.google.dagger", "hilt-android").versionRef("dagger")
            alias("hilt-android-compiler").to("com.google.dagger", "hilt-android-compiler").versionRef("dagger")

            // UI
            alias("androidx-constraintlayout").to("androidx.constraintlayout:constraintlayout:2.1.0-beta02")
            alias("material").to("com.google.android.material:material:1.3.0")
            alias("coil").to("io.coil-kt", "coil").versionRef("coil")
            alias("coil-gif").to("io.coil-kt", "coil-gif").versionRef("coil")
            alias("coil-svg").to("io.coil-kt", "coil-svg").versionRef("coil")
            alias("lottie").to("com.airbnb.android:lottie:3.7.0")
            alias("keyboardvisibilityevent").to("net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:3.0.0-RC3")

            // Network
            alias("okhttp").to("com.squareup.okhttp3:okhttp:4.9.1")
            alias("retrofit").to("com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            alias("retrofit-adapter-rxjava").to("com.squareup.retrofit2", "adapter-rxjava3").versionRef("retrofit")
            alias("retrofit-converter-moshi").to("com.squareup.retrofit2", "converter-moshi").versionRef("retrofit")
            alias("retrofit-converter-scalars").to("com.squareup.retrofit2", "converter-scalars").versionRef("retrofit")
            alias("moshi").to("com.squareup.moshi:moshi-kotlin:1.12.0")

            // Rx
            alias("rxjava").to("io.reactivex.rxjava3:rxjava:3.0.13")
            alias("rxkotlin").to("io.reactivex.rxjava3:rxkotlin:3.0.1")
            alias("rxandroid").to("io.reactivex.rxjava3:rxandroid:3.0.0")
            alias("rxrelay").to("com.jakewharton.rxrelay3:rxrelay:3.0.0")
            alias("rxbinding").to("com.jakewharton.rxbinding4", "rxbinding").versionRef("rxbinding")
            alias("rxbinding-material").to("com.jakewharton.rxbinding4", "rxbinding-material").versionRef("rxbinding")

            // JVM test
            alias("junit4").to("junit:junit:4.13")
            alias("junit5").to("org.junit.jupiter:junit-jupiter:5.7.2")
            alias("mockk").to("io.mockk", "mockk").versionRef("mockk")

            // Android test
            alias("test-core").to("androidx.test:core:1.0.0")
            alias("test-runner").to("androidx.test.ext:junit-ktx:1.1.2")
            alias("test-rules").to("androidx.test:rules:1.1.0")
            alias("test-truth").to("androidx.test.ext:truth:1.0.0")
            alias("robolectric").to("org.robolectric:robolectric:4.5.1")
            alias("mockk-android").to("io.mockk", "mockk-android").versionRef("mockk")
            alias("espresso-core").to("androidx.test.espresso", "espresso-core").versionRef("espresso")
            alias("espresso-contrib").to("androidx.test.espresso", "espresso-contrib").versionRef("espresso")

            // Misc
            alias("timber").to("com.jakewharton.timber:timber:4.7.1")
            alias("detekt-formatting").to("io.gitlab.arturbosch.detekt", "detekt-formatting").versionRef("detekt")
            alias("insetter").to("dev.chrisbanes.insetter:insetter:0.6.0")
            alias("desugar-jdk-libs").to("com.android.tools:desugar_jdk_libs:1.1.5")
        }
    }
}
*/
