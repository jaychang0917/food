// == Define locations for build logic ==
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() 
    }
    includeBuild("../../build-logic")
}

// == Define locations for components ==
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "feature-order"
include("order")
