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

package com.jaychang.food.feature.country.internal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import com.jaychang.food.core.arch.observeEvent
import com.jaychang.food.base.BaseFragment
import com.jaychang.food.core.arch.Navigator
import com.jaychang.food.feature.country.CountrySelectionArgs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class CountrySelectionFragment : BaseFragment() {
    @Inject
    lateinit var router: CountrySelectionRouter

    @Inject
    lateinit var navigator: Navigator

    private val viewModel: CountrySelectionViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return CountrySelectionView(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view as CountrySelectionView, savedInstanceState)
        val args = navigator.argumentsOf<CountrySelectionArgs>(this)
        viewModel.init(args)
        view.setViewModel(viewLifecycleOwner, viewModel)
        observeNavigation()
    }

    private fun observeNavigation() {
        viewModel.uiState.map { it.navigation }.observeEvent(viewLifecycleOwner) {
            when (it) {
                is Navigation.Close -> router.close()
            }
        }
    }
}
