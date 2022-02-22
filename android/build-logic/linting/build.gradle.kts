plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(project(":dependencies"))
    implementation("org.jlleitschuh.gradle:ktlint-gradle:7.4.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.1")
}
