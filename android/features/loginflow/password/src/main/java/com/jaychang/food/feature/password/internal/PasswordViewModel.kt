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

package com.jaychang.food.feature.password.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaychang.food.core.subscribeResult
import com.jaychang.food.data.SessionLoginResult
import com.jaychang.food.data.SessionRepository
import com.jaychang.food.feature.flow.login.LoginFlowContext
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
internal class PasswordViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {
    lateinit var loginFlowContext: LoginFlowContext

    private val disposables = CompositeDisposable()

    private val _navigation = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    private val _isLoadingBarVisible = MutableLiveData(false)
    val isLoadingBarVisible: LiveData<Boolean> = _isLoadingBarVisible

    private val _isNextButtonVisible = MutableLiveData(false)
    val isNextButtonVisible: LiveData<Boolean> = _isNextButtonVisible

    private val _isNextButtonEnabled = MutableLiveData(false)
    val isNextButtonEnabled: LiveData<Boolean> = _isNextButtonEnabled

    private val _isErrorViewVisible = MutableLiveData(false)
    val isErrorViewVisible: LiveData<Boolean> = _isErrorViewVisible

    fun onPasswordChanged(password: String) {
        _isErrorViewVisible.value = false
        _isNextButtonVisible.value = password.isNotEmpty()
        _isNextButtonEnabled.value = password.isNotEmpty()
    }

    fun onNextButtonClicked(password: String) {
        _isLoadingBarVisible.value = true
        _isNextButtonEnabled.value = false

        sessionRepository.login(loginFlowContext.data.phoneNumber, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeResult {
                _isLoadingBarVisible.value = false
                _isNextButtonEnabled.value = true

                when (it) {
                    is SessionLoginResult.Success -> {
                        _isErrorViewVisible.value = false
                        _navigation.value = Navigation.Main()
                    }
                    SessionLoginResult.Error.InvalidPassword -> {
                        _isErrorViewVisible.value = true
                    }
                }
            }
            .addTo(disposables)
    }

    fun back() {
        _navigation.value = Navigation.Back()
    }

    override fun onCleared() {
        disposables.clear()
    }
}
