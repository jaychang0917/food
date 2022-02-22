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

rootProject.name = "common-ui"
include("ui")
include("banner")
include("button")
include("core")
include("header")
include("loading")
include("textfield")
