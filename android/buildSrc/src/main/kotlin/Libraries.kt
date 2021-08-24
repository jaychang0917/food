/*
 * Copyright (C) 2021. Jay Chang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

const val KOTLIN_VERSION = "1.5.21"
const val LIFECYCLE_VERSION = "2.3.1"
const val DAGGER_VERSION = "2.37"
const val RETROFIT_VERSION = "2.9.0"
const val RXBINDING_VERSION = "4.0.0"
const val MOCKK_VERSION = "1.11.0"
const val ESPRESSO_VERSION = "3.1.0"
const val FLIPPER_VERSION = "0.92.0"
const val DETEKT_VERSION = "1.17.1"
const val COIL_VERSION = "1.2.2"

object Libraries {
    // Kotlin
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION"
    const val KOTLIN_SERIALIZATION_JSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1"

    // Android
    const val ANDROID_CORE = "androidx.core:core-ktx:1.3.2"
    const val APPCOMPAT = "androidx.appcompat:appcompat:1.3.0"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:1.3.4"
    const val CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:2.1.0-beta02"
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:1.2.1"
    const val VIEWPAGER2 = "androidx.viewpager2:viewpager2:1.0.0"
    const val MATERIAL = "com.google.android.material:material:1.3.0"
    const val SECURITY_CRYPTO = "androidx.security:security-crypto-ktx:1.1.0-alpha03"
    const val PLAY_CORE = "com.google.android.play:core-ktx:1.8.1"
    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION"
    const val LIFECYCLE_PROCESS = "androidx.lifecycle:lifecycle-process:$LIFECYCLE_VERSION"
    const val LIFECYCLE_COMMON = "androidx.lifecycle:lifecycle-common-java8:$LIFECYCLE_VERSION"
    const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION"
    const val DAGGER = "com.google.dagger:dagger:$DAGGER_VERSION"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:$DAGGER_VERSION"
    const val HILT = "com.google.dagger:hilt-android:$DAGGER_VERSION"
    const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:$DAGGER_VERSION"
    const val ANNOTATION = "androidx.annotation:annotation:1.2.0"

    // Image
    const val COIL = "io.coil-kt:coil:$COIL_VERSION"
    const val COIL_GIF = "io.coil-kt:coil-gif:$COIL_VERSION"
    const val COIL_SVG = "io.coil-kt:coil-svg:$COIL_VERSION"
    const val LOTTIE = "com.airbnb.android:lottie:3.7.0"

    // Logger
    const val TIMBER = "com.jakewharton.timber:timber:4.7.1"

    // Network
    const val OKHTTP = "com.squareup.okhttp3:okhttp:4.9.1"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
    const val RETROFIT_ADAPTER_RXJAVA3 = "com.squareup.retrofit2:adapter-rxjava3:$RETROFIT_VERSION"
    const val RETROFIT_CONVERTER_SERIALIZATION = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    const val MOSHI = "com.squareup.moshi:moshi-kotlin:1.12.0"

    // Firebase
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:28.1.0"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics-ktx"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
    const val FIREBASE_DYNAMIC_LINKS = "com.google.firebase:firebase-dynamic-links-ktx"

    // Rx
    const val RXJAVA3 = "io.reactivex.rxjava3:rxjava:3.0.13"
    const val RXKOTLIN3 = "io.reactivex.rxjava3:rxkotlin:3.0.1"
    const val RXANDROID = "io.reactivex.rxjava3:rxandroid:3.0.0"
    const val RXRELAY = "com.jakewharton.rxrelay3:rxrelay:3.0.0"
    const val RXBINDING_PLATFORM = "com.jakewharton.rxbinding4:rxbinding:$RXBINDING_VERSION"
    const val RXBINDING_MATERIAL = "com.jakewharton.rxbinding4:rxbinding-material:$RXBINDING_VERSION"

    // JVM Test
    const val JUNIT4 = "junit:junit:4.13"
    const val JUNIT5 = "org.junit.jupiter:junit-jupiter:5.7.2"
    const val MOCKK = "io.mockk:mockk:$MOCKK_VERSION"

    // Android Test
    const val ANDROID_TEST_CORE = "androidx.test:core:1.0.0"
    const val ANDROID_TEST_RUNNER = "androidx.test.ext:junit-ktx:1.1.2"
    const val ANDROID_TEST_RULE = "androidx.test:rules:1.1.0"
    const val TRUTH_ASSERT = "androidx.test.ext:truth:1.0.0"
    const val MOCKK_ANDROID = "io.mockk:mockk-android:$MOCKK_VERSION"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:$ESPRESSO_VERSION"
    const val ESPRESSO_CORE_CONTRIB = "androidx.test.espresso:espresso-contrib:$ESPRESSO_VERSION"
    const val ROBOLECTRIC = "org.robolectric:robolectric:4.5.1"

    // DevTool
    const val FLIPPER = "com.facebook.flipper:flipper:$FLIPPER_VERSION"
    const val SOLOADER = "com.facebook.soloader:soloader:0.10.1"
    const val FLIPPER_NETWORK = "com.facebook.flipper:flipper-network-plugin:$FLIPPER_VERSION"
    const val FLIPPER_LEAKCANARY = "com.facebook.flipper:flipper-leakcanary-plugin:$FLIPPER_VERSION"
    const val LEAKCANARY = "com.squareup.leakcanary:leakcanary-android:2.7"
    const val DETEKT_FORMATTING = "io.gitlab.arturbosch.detekt:detekt-formatting:$DETEKT_VERSION"

    // Misc
    const val INSETTER = "dev.chrisbanes.insetter:insetter:0.6.0"
    const val DESUGAR = "com.android.tools:desugar_jdk_libs:1.1.5"
    const val IME_VISIBILITY_LISTENER = "net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:3.0.0-RC3"
}
