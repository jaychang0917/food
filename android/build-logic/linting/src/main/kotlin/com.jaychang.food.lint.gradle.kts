import io.gitlab.arturbosch.detekt.detekt

plugins {
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    config = files("$rootDir/build-logic/linting/detekt-config.yml")
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
}

dependencies {
    detektPlugins(Libraries.DETEKT_FORMATTING)
}
