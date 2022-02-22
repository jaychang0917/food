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

package com.jaychang.food.feature.landing.internal

import com.jaychang.food.core.arch.Flow
import com.jaychang.food.core.arch.Navigator
import com.jaychang.food.feature.country.OnCountrySelectListener
import com.jaychang.food.feature.flow.login.LoginFlowBuilder
import com.jaychang.food.feature.flow.login.LoginFlowContext
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
internal class LandingRouter @Inject constructor(
    private val navigator: Navigator
) {
    private lateinit var loginFlow: Flow<*>

    fun attachLoginFlow() {
        val onFinished = { _: LoginFlowContext.Data ->
            // Detach login flow pages
            detachLoginFlow()
            // Detach landing page
            navigator.pop()
        }
        loginFlow = LoginFlowBuilder(onFinished).build()
        navigator.start(loginFlow, arguments = loginFlow.context)
    }

    private fun detachLoginFlow() {
        navigator.stop(loginFlow)
    }
}
