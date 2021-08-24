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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jaychang.food.base.BaseFragment
import com.jaychang.food.core.arch.observeEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class MainFragment : BaseFragment() {
    @Inject
    lateinit var router: MainRouter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return MainView(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view as MainView).setViewModel(viewLifecycleOwner, viewModel)

        router.init()

        viewModel.navigation.observeEvent(viewLifecycleOwner) {
            navigate(it)
        }
    }

    private fun navigate(navigation: Navigation) {
        when (navigation) {
            is Navigation.Home -> router.activateHomePage()
            is Navigation.Search -> router.activateSearchPage()
            is Navigation.Order -> router.activateOrderPage()
            is Navigation.Profile -> router.activateProfilePage()
        }
    }
}
