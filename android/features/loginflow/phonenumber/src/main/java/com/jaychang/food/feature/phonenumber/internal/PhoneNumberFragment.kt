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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jaychang.food.core.arch.observeEvent
import com.jaychang.food.base.BaseFragment
import com.jaychang.food.core.arch.Navigator
import com.jaychang.food.core.util.SoftKeyboard
import com.jaychang.food.feature.flow.login.LoginFlowContext
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class PhoneNumberFragment : BaseFragment() {
    @Inject
    lateinit var router: PhoneNumberRouter

    @Inject
    lateinit var navigator: Navigator
    private lateinit var loginFlowContext: LoginFlowContext

    private val viewModel: PhoneNumberViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PhoneNumberView(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view as PhoneNumberView, savedInstanceState)
        SoftKeyboard.show(view.editText)

        loginFlowContext = navigator.argumentsOf(this)
        viewModel.loginFlowContext = loginFlowContext

        view.setViewModel(viewLifecycleOwner, viewModel)

        viewModel.navigation.observeEvent(viewLifecycleOwner) {
            navigate(it)
        }
    }

    private fun navigate(navigation: Navigation) {
        when (navigation) {
            is Navigation.Password -> router.attachPasswordPage(loginFlowContext)
            is Navigation.CountrySelection -> router.attachCountrySelectionPage(navigation.country, viewModel)
            is Navigation.Close -> router.close()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        SoftKeyboard.dismiss((view as PhoneNumberView).editText)
    }
}
