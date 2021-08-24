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

fun includeProject(moduleName: String, path: String) {
    settings.include(moduleName)
    project(moduleName).projectDir = File(path)
}

// App
includeProject(":food", "food")

// Business flow
includeProject(":loginflow", "features/loginflow")
includeProject(":loginflow:shared", "features/loginflow/shared")
includeProject(":loginflow:phonenumber", "features/loginflow/phonenumber")
includeProject(":loginflow:password", "features/loginflow/password")

// Independent page
includeProject(":root", "features/root")
includeProject(":landing", "features/landing")
includeProject(":country", "features/country")
includeProject(":main", "features/main")
includeProject(":home", "features/home")
includeProject(":order", "features/order")
includeProject(":search", "features/search")
includeProject(":profile", "features/profile")

// Common
includeProject(":core", "common/core")
includeProject(":base", "common/base")
includeProject(":data", "common/data")
includeProject(":api", "common/api")
includeProject(":ui", "common/ui")
includeProject(":ui:core", "common/ui/core")
includeProject(":ui:loading", "common/ui/loading")
includeProject(":ui:textfield", "common/ui/textfield")
includeProject(":ui:header", "common/ui/header")
includeProject(":ui:banner", "common/ui/banner")
includeProject(":ui:button", "common/ui/button")
