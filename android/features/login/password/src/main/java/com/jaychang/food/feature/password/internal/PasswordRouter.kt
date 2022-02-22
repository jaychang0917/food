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

package com.jaychang.food.feature.password.internal

import com.jaychang.food.core.arch.Navigator
import com.jaychang.food.feature.flow.login.LoginFlowContext
import com.jaychang.food.feature.main.MainPageBuilder
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
internal class PasswordRouter @Inject constructor(
    private val navigator: Navigator
) {
    fun attachMainPage(loginFlowContext: LoginFlowContext) {
        loginFlowContext.finish()
        val mainPage = MainPageBuilder().build()
        navigator.push(mainPage, addToBackStack = false)
    }

    fun back() {
        navigator.pop()
    }
}
