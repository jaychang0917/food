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

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaychang.food.core.util.update
import com.jaychang.food.feature.main.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableLiveData(MainUiState())
    val uiState: LiveData<MainUiState> = _uiState

    fun onBottomTabSelected(menu: MenuItem): Boolean {
        val navigation = when (menu.itemId) {
            R.id.home -> Navigation.Home()
            R.id.search -> Navigation.Search()
            R.id.order ->  Navigation.Order()
            R.id.profile -> Navigation.Profile()
            else -> error("No support for tab ${menu.title}")
        }
        _uiState.update {
            it.copy(navigation = navigation)
        }

        return true
    }
}
