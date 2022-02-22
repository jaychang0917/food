plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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
