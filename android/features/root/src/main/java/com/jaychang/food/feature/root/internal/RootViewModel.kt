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

package com.jaychang.food.feature.root.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaychang.food.core.subscribeResult
import com.jaychang.food.data.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _navigation = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    fun nextPage() {
        sessionRepository.isLoggedIn()
            .subscribeResult { loggedIn ->
                if (loggedIn) {
                    _navigation.value = Navigation.Home()
                } else {
                    _navigation.value = Navigation.Landing()
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }
}
