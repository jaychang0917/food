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

package com.jaychang.food.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import com.jaychang.food.core.UncaughtErrorHandler
import com.jaychang.food.core.util.Logger
import com.jaychang.ui.banner.Banner
import java.net.UnknownHostException

object UncaughtErrorHandlers {
    class Logging : UncaughtErrorHandler {
        override fun handle(error: Throwable) {
            Logger.error("Uncaught error", error = error)
        }
    }

    class NetworkLost(private val activity: AppCompatActivity) : UncaughtErrorHandler {
        override fun handle(error: Throwable) {
            if (error !is UnknownHostException) return

            val parentViewGroup = activity.findViewById<ContentFrameLayout>(android.R.id.content)
            val message = activity.getString(R.string.network_lost_message)
            Banner.error(parentViewGroup, message).show()
        }
    }
}
