plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(project(":dependencies"))
    implementation(project(":test"))
    implementation(project(":linting"))
    implementation("com.android.tools.build:gradle:7.0.3")
    implementation("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:1.5.31")
}
