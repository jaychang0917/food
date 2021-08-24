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

package com.jaychang.food

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.jaychang.food.base.BaseActivity
import com.jaychang.food.base.UncaughtErrorHandlers.Logging
import com.jaychang.food.base.UncaughtErrorHandlers.NetworkLost
import com.jaychang.food.core.UncaughtErrorHandlerRegistry
import com.jaychang.food.core.arch.Navigator
import com.jaychang.food.core.util.WindowDecorationHandler
import com.jaychang.food.feature.root.RootBuilder
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@AndroidEntryPoint
class FoodActivity : BaseActivity() {
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Food)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        if (savedInstanceState == null) {
            handleUncaughtError()
        }

        attachRoot()
    }

    private fun handleUncaughtError() {
        UncaughtErrorHandlerRegistry.addHandler(Logging())
        UncaughtErrorHandlerRegistry.addHandler(NetworkLost(this))
    }

    private fun attachRoot() {
        val rootBuilder = RootBuilder()
        val rootFragment = rootBuilder.build()
        navigator.push(rootFragment, addToBackStack = false)
    }

    override fun onBackPressed() {
        navigator.backDispatcher.handleBack(this)
    }

    @InstallIn(ActivityComponent::class)
    @dagger.Module
    object Module {
        @ActivityScoped
        @Provides
        fun appNavigator(activity: FragmentActivity): Navigator {
            return Navigator(activity.supportFragmentManager, R.id.fragmentContainer)
        }

        @ActivityScoped
        @Provides
        fun windowDecorationHandler(activity: FragmentActivity): WindowDecorationHandler {
            return WindowDecorationHandler(activity.window)
        }
    }
}
