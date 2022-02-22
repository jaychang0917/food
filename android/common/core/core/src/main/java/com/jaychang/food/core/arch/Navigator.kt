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

package com.jaychang.food.core.arch

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.commit

class Navigator(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) {
    // TODO doesn't handle configuration case, the data will be lost after rotate screen.
    private val argumentsMap = mutableMapOf<Fragment, Any>()

    val backDispatcher = BackDispatcher()

    fun push(page: Fragment, addToBackStack: Boolean = true, arguments: Any = Unit) {
        if (arguments != Unit) {
            argumentsMap[page] = arguments
        }

        fragmentManager.commit {
            replace(containerId, page)
            if (addToBackStack) {
                setReorderingAllowed(true)
                addToBackStack(page.javaClass.name)
            }
        }
    }

    fun pop() {
        val lastPage = fragmentManager.fragments.last()
        argumentsMap.remove(lastPage)
        fragmentManager.popBackStack()
    }

    fun pop(page: Fragment) {
        argumentsMap.remove(page)
        fragmentManager.popBackStack(page.javaClass.name, POP_BACK_STACK_INCLUSIVE)
    }

    fun start(flow: Flow<*>, arguments: Any = Unit) {
        push(flow.startDestination, arguments = arguments)
    }

    fun stop(flow: Flow<*>) {
        pop(flow.startDestination)
    }

    /**
     * Get the arguments of [page].
     *
     * NOT handle activity recreation due to config change or process killed by OS cases.
     * */
    fun <T : Any> argumentsOf(page: Fragment): T {
       return argumentsMap[page] as T
    }

    inner class BackDispatcher {
        private val callbacks = mutableListOf<BackCallback>()

        fun handleBack(activity: AppCompatActivity) {
            val consumed = callbacks.asReversed().any { it.onBack() }
            if (!consumed) {
                val noEntryInBackStack = activity.supportFragmentManager.backStackEntryCount <= 0
                val backToRoot = activity.supportFragmentManager.fragments.size == 2
                if (noEntryInBackStack || backToRoot) {
                    activity.finish()
                } else {
                    pop()
                }
            }
        }

        fun addCallback(callback: BackCallback) {
            callbacks.add(callback)
        }

        fun removeCallback(callback: BackCallback) {
            callbacks.remove(callback)
        }
    }

    interface BackCallback {
        /**
         * @return true to consume the click event; false default handling.
         * */
        fun onBack(): Boolean
    }
}
