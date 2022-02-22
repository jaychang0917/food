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

plugins {
    id("com.jaychang.food.application")
    id("com.google.gms.google-services")
}

android {
    defaultConfig {
        applicationId = "com.jaychang.food"
        versionCode = properties["versionCode"]?.toString()?.toInt() ?: 1
        versionName = properties["versionName"]?.toString() ?: ""
    }

    val uploadKeystore = file("../buildSrc/secrets/upload.keystore")

    signingConfigs {
        getByName("debug") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        if (uploadKeystore.exists()) {
            create("upload") {
                storeFile = uploadKeystore
                storePassword = System.getenv("FOOD_RELEASE_STORE_PASSWORD")
                keyAlias = "food"
                keyPassword = System.getenv("FOOD_RELEASE_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            // CI server will build with debug signing config.
            signingConfig = if (uploadKeystore.exists()) {
                signingConfigs.getByName("upload")
            } else {
                signingConfigs.getByName("debug")
            }

            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    // https://dagger.dev/hilt/gradle-setup#classpath-aggregation
    lintOptions {
        isCheckReleaseBuilds = false
    }
}

// https://dagger.dev/hilt/gradle-setup#classpath-aggregation
hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {
    // App theme res are placed in base module
    implementation(Libraries.APPCOMPAT)
    implementation(Libraries.FRAGMENT)
    implementation("common.core:core")
    implementation("common.base:base")
    implementation("common.data:data")
    implementation("common.ui:ui")
    implementation("feature.root:root")
}
