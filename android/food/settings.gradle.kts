// == Define locations for build logic ==
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() 
    }

    includeBuild("../build-logic")
}

// == Define locations for components ==
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "food"
includeBuild("../common/base")
includeBuild("../common/core")
includeBuild("../common/data")
includeBuild("../common/ui")
includeBuild("../features/root")
include("app")
