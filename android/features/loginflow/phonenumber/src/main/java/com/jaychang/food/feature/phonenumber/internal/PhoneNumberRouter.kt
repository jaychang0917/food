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

package com.jaychang.food.feature.phonenumber.internal

import androidx.fragment.app.Fragment
import com.jaychang.food.api.models.Country
import com.jaychang.food.core.arch.Navigator
import com.jaychang.food.feature.country.CountrySelectionArgs
import com.jaychang.food.feature.country.CountrySelectionPageBuilder
import com.jaychang.food.feature.country.OnCountrySelectListener
import com.jaychang.food.feature.flow.login.LoginFlowContext
import com.jaychang.food.feature.password.PasswordPageBuilder
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
internal class PhoneNumberRouter @Inject constructor(
    private val navigator: Navigator
) {
    private lateinit var countrySelection: Fragment
    private lateinit var password: Fragment

    fun attachPasswordPage(loginFlowContext: LoginFlowContext) {
        password = PasswordPageBuilder().build()
        navigator.push(password, arguments = loginFlowContext)
    }

    fun detachPasswordPage() {
        navigator.pop(password)
    }

    fun attachCountrySelectionPage(
        selectedCountry: Country,
        onCountrySelectListener: OnCountrySelectListener
    ) {
        val args = CountrySelectionArgs(selectedCountry, onCountrySelectListener)
        countrySelection = CountrySelectionPageBuilder().build()
        navigator.push(countrySelection, arguments = args)
    }

    fun detachCountrySelectionPage() {
        navigator.pop(countrySelection)
    }

    fun close() {
        navigator.pop()
    }
}
