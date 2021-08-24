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

package com.jaychang.food.core.util

import android.graphics.Color
import android.view.View
import android.view.Window

data class WindowDecoration internal constructor(val statusBarColor: Int, val systemUiFlag: Int) {
    companion object {
        fun layoutFullScreen(): WindowDecoration {
            val systemUiFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.withFlag(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            val statusBarColor = Color.TRANSPARENT
            return WindowDecoration(statusBarColor, systemUiFlag)
        }

        fun layoutImmerse(): WindowDecoration {
            val systemUiFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                .withFlag(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
                .withFlag(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            val statusBarColor = Color.TRANSPARENT
            return WindowDecoration(statusBarColor, systemUiFlag)
        }

        fun fullScreen(): WindowDecoration {
            val systemUiFlag = View.SYSTEM_UI_FLAG_FULLSCREEN
            return WindowDecoration(-1, systemUiFlag)
        }

        fun immerse(): WindowDecoration {
            val systemUiFlag = View.SYSTEM_UI_FLAG_FULLSCREEN.withFlag(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
            return WindowDecoration(-1, systemUiFlag)
        }
    }
}

class WindowDecorationHandler(private val window: Window) {
    private var defaultWindowDecoration = WindowDecoration(window.statusBarColor, window.decorView.systemUiVisibility)
    private var _currentWindowDecoration = defaultWindowDecoration

    val currentWindowDecoration: WindowDecoration
        get() = _currentWindowDecoration

    fun apply(decoration: WindowDecoration) {
        window.statusBarColor = decoration.statusBarColor
        window.decorView.systemUiVisibility = decoration.systemUiFlag
        _currentWindowDecoration = decoration
    }

    fun applyDefault() {
        apply(defaultWindowDecoration)
    }

    fun saveState() = _currentWindowDecoration

    fun restoreState(state: WindowDecoration) {
        _currentWindowDecoration = state
        defaultWindowDecoration = state
    }
}
