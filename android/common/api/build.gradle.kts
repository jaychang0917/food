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
    id(Plugins.ANDROID_LIB_ID)
    id(Plugins.KOTLIN_ANDROID_ID)
    id(Plugins.KOTLIN_KAPT_ID)
    id(Plugins.HILT_ID)
}

setupLibraryModule {
    buildTypes {
        // TODO deploy
        val devApi = "\"http://192.168.2.1:3000/api\""
        val productionApi = "\"https://food-mockserver.herokuapp.com/api/\""

        getByName("debug") {
            buildConfigField("String", "API_BASE_URL", devApi)
        }

        getByName("release") {
            buildConfigField("String", "API_BASE_URL", productionApi)
        }
    }
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.HILT)
    kapt(Libraries.HILT_COMPILER)

    // FIXME remove after published api client to maven
    implementation(Libraries.OKHTTP)
    implementation(Libraries.RETROFIT)
    implementation(Libraries.MOSHI)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("io.reactivex.rxjava3:rxjava:3.0.13")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:$RETROFIT_VERSION")
    implementation("com.squareup.retrofit2:converter-scalars:$RETROFIT_VERSION")
}
