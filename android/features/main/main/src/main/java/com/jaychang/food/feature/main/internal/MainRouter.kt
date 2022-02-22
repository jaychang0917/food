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

package com.jaychang.food.feature.main.internal

import com.jaychang.food.feature.home.HomePageBuilder
import com.jaychang.food.feature.order.OrderPageBuilder
import com.jaychang.food.feature.profile.ProfilePageBuilder
import com.jaychang.food.feature.search.SearchPageBuilder
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
internal class MainRouter @Inject constructor(
    private val navigator: MainNavigator
) {
    private val homePage = HomePageBuilder().build()
    private val searchPage = SearchPageBuilder().build()
    private val orderPage = OrderPageBuilder().build()
    private val profilePage = ProfilePageBuilder().build()

    fun initPages() {
        val pages = listOf(homePage, searchPage, orderPage, profilePage)
        navigator.init(pages)
    }

    fun activateHomePage() {
        navigator.activate(homePage)
    }

    fun activateSearchPage() {
        navigator.activate(searchPage)
    }

    fun activateOrderPage() {
        navigator.activate(orderPage)
    }

    fun activateProfilePage() {
        navigator.activate(profilePage)
    }
}
