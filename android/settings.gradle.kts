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

rootProject.name = "food-android"

// Define locations for build logic
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    includeBuild("build-logic")
}

includeBuild("food")

includeBuild("common/api")
includeBuild("common/base")
includeBuild("common/core")
includeBuild("common/data")
includeBuild("common/ui")

includeBuild("features/country")
includeBuild("features/home")
includeBuild("features/landing")
includeBuild("features/login")
includeBuild("features/main")
includeBuild("features/order")
includeBuild("features/profile")
includeBuild("features/root")
includeBuild("features/search")
