plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.jaychang.food.lint")
    id("com.jaychang.food.test")
}

android {
    compileSdk = AndroidBuilds.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AndroidBuilds.MIN_SDK_VERSION
        targetSdk = AndroidBuilds.TARGET_SDK_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation("common.ui:ui")
    implementation("common.core:core")
    implementation("common.base:base")
    implementation("common.data:data")

    implementation(Libraries.KOTLIN_STDLIB)
    implementation(Libraries.ANDROID_CORE)
    implementation(Libraries.APPCOMPAT)
    implementation(Libraries.FRAGMENT)
    implementation(Libraries.CONSTRAINTLAYOUT)
    implementation(Libraries.LIFECYCLE_RUNTIME)
    implementation(Libraries.LIFECYCLE_PROCESS)
    implementation(Libraries.LIFECYCLE_COMMON)
    implementation(Libraries.LIFECYCLE_VIEWMODEL)
    implementation(Libraries.LIFECYCLE_LIVEDATA)
    implementation(Libraries.ANNOTATION)
    implementation(Libraries.RXJAVA3)
    implementation(Libraries.RXKOTLIN3)
    implementation(Libraries.RXRELAY)
    implementation(Libraries.RXBINDING_PLATFORM)
    implementation(Libraries.RXBINDING_MATERIAL)

    implementation(Libraries.DAGGER)
    kapt(Libraries.DAGGER_COMPILER)
    implementation(Libraries.HILT)
    kapt(Libraries.HILT_COMPILER)
}
