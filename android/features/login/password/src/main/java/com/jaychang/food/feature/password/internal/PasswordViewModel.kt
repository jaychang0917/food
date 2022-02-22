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
import com.jaychang.food.core.util.update
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

    private val _uiState = MutableLiveData(PasswordUiState())
    val uiState: LiveData<PasswordUiState> = _uiState

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(
                isErrorViewVisible = false,
                isNextButtonVisible = password.isNotEmpty(),
                isNextButtonEnabled = password.isNotEmpty()
            )
        }
    }

    fun onNextButtonClicked(password: String) {
        _uiState.update {
            it.copy(
                isLoadingBarVisible = true,
                isNextButtonEnabled = false
            )
        }

        sessionRepository.login(loginFlowContext.data.phoneNumber, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeResult { result ->
                _uiState.update {
                    it.copy(
                        isLoadingBarVisible = false,
                        isNextButtonEnabled = true
                    )
                }

                when (result) {
                    is SessionLoginResult.Success -> {
                        _uiState.update {
                            it.copy(
                                isErrorViewVisible = false,
                                navigation = Navigation.Main()
                            )
                        }
                    }
                    SessionLoginResult.Error.InvalidPassword -> {
                        _uiState.update {
                            it.copy(isErrorViewVisible = true)
                        }
                    }
                }
            }
            .addTo(disposables)
    }

    fun back() {
        _uiState.update {
            it.copy(navigation = Navigation.Back())
        }
    }

    override fun onCleared() {
        disposables.clear()
    }
}
