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

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.setupFeatureModule(block: LibraryExtension.() -> Unit = {}) {
    setupBaseModule<LibraryExtension> {
        buildFeatures {
            viewBinding = true
            dataBinding = true
        }

        testOptions {
            unitTests.isIncludeAndroidResources = true
        }

        block()
    }

    dependencies.addCommonDependencies()
    dependencies.add("implementation", project(":core"))
    dependencies.add("implementation", project(":base"))
    dependencies.add("implementation", project(":ui"))
    dependencies.add("implementation", project(":data"))
}

private fun DependencyHandler.addCommonDependencies() {
    val implementations = listOf(
        Libraries.KOTLIN_STDLIB,
        Libraries.ANDROID_CORE,
        Libraries.APPCOMPAT,
        Libraries.FRAGMENT,
        Libraries.CONSTRAINTLAYOUT,
        Libraries.LIFECYCLE_RUNTIME,
        Libraries.LIFECYCLE_PROCESS,
        Libraries.LIFECYCLE_COMMON,
        Libraries.LIFECYCLE_VIEWMODEL,
        Libraries.LIFECYCLE_LIVEDATA,
        Libraries.DAGGER,
        Libraries.HILT,
        Libraries.ANNOTATION,
        Libraries.RXJAVA3,
        Libraries.RXKOTLIN3,
        Libraries.RXRELAY,
        Libraries.RXBINDING_PLATFORM,
        Libraries.RXBINDING_MATERIAL
    )
    implementations.forEach {
        add("implementation", it)
    }

    val compilers = listOf(Libraries.DAGGER_COMPILER, Libraries.HILT_COMPILER)
    compilers.forEach {
        add("kapt", it)
    }
}

fun Project.setupLibraryModule(androidExtBlock: LibraryExtension.() -> Unit = {}) {
    setupBaseModule<LibraryExtension> {
        buildFeatures {
            viewBinding = true
            dataBinding = true
        }

        testOptions {
            unitTests.isIncludeAndroidResources = true
        }

        androidExtBlock()
    }
}

fun Project.setupAppModule(androidExtBlock: BaseAppModuleExtension.() -> Unit = {}) {
    setupBaseModule<BaseAppModuleExtension> {
        buildFeatures {
            viewBinding = true
            dataBinding = true
        }

        compileOptions.isCoreLibraryDesugaringEnabled = true

        androidExtBlock()
    }

    dependencies.add("coreLibraryDesugaring", Libraries.DESUGAR)
}

private inline fun <reified T : BaseExtension> Project.setupBaseModule(crossinline block: T.() -> Unit = {}) {
    extensions.configure<BaseExtension>("android") {
        compileSdkVersion(AndroidBuilds.COMPILE_SDK_VERSION)

        defaultConfig {
            minSdkVersion(AndroidBuilds.MIN_SDK_VERSION)
            targetSdkVersion(AndroidBuilds.TARGET_SDK_VERSION)
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        testOptions {
            unitTests.all {
                it.useJUnitPlatform()
            }
        }

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }

        (this as T).block()
    }

    dependencies.addTestDependencies()
    dependencies.addAndroidTestDependencies()
}

private fun DependencyHandler.addTestDependencies() {
    val implementations = listOf(
        Libraries.JUNIT4,
        Libraries.JUNIT5,
        Libraries.MOCKK,
        Libraries.TRUTH_ASSERT,
        Libraries.ANDROID_TEST_RUNNER,
        Libraries.ANDROID_TEST_CORE, // Delegate to Robolectric under the hood.
        Libraries.ROBOLECTRIC
    )

    implementations.forEach {
        add("testImplementation", it)
    }
}

private fun DependencyHandler.addAndroidTestDependencies() {
    val implementations = listOf(
        Libraries.ANDROID_TEST_CORE,
        Libraries.ANDROID_TEST_RULE,
        Libraries.ANDROID_TEST_RUNNER,
        Libraries.TRUTH_ASSERT,
        Libraries.MOCKK_ANDROID,
        Libraries.ESPRESSO_CORE,
        Libraries.ESPRESSO_CORE_CONTRIB
    )

    implementations.forEach {
        add("androidTestImplementation", it)
    }
}

private fun BaseExtension.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

fun Project.setupLint() {
    setupKtlint()
    setupDetekt()
}

private fun Project.setupKtlint() {
    apply(plugin = Plugins.KTLINT_ID)
}

private fun Project.setupDetekt(block: DetektExtension.() -> Unit = {}) {
    apply(plugin = Plugins.DETEKT_ID)

    extensions.configure<DetektExtension>("detekt") {
        config = files("$rootDir/lint/detekt-config.yml")
        reports {
            xml {
                enabled = true
                destination = file("build/reports/detekt/lint-results.xml")
            }
            html {
                enabled = false
            }
            txt {
                enabled = false
            }
        }
        autoCorrect = true
        parallel = true

        dependencies.add("detektPlugins", Libraries.DETEKT_FORMATTING)

        block()
    }
}

fun Project.setupKotlinModule() {
    tasks.withType(KotlinCompile::class.java) {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }

    tasks.withType(JavaCompile::class.java) {
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    }
}
