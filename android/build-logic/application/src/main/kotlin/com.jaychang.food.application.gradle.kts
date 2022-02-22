plugins {
    id("com.jaychang.food.lint")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
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
        isCoreLibraryDesugaringEnabled = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(Libraries.HILT)
    kapt(Libraries.HILT_COMPILER)
    coreLibraryDesugaring(Libraries.DESUGAR)
}
