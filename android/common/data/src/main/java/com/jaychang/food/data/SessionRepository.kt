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

package com.jaychang.food.data

import com.jaychang.food.api.apis.SessionApi
import com.jaychang.food.api.models.Session
import com.jaychang.food.api.models.SessionRequestBody
import com.jaychang.food.api.toApiError
import com.jaychang.food.data.SessionLoginResult.Error.InvalidPassword
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

sealed class SessionLoginResult {
    data class Success(val data: Session) : SessionLoginResult()
    sealed class Error : SessionLoginResult() {
        object InvalidPassword : Error() {
            const val code = "user_invalid_password"
        }
    }
}

@Singleton
class SessionRepository @Inject constructor(
    private val sessionApi: SessionApi,
    private val sessionManager: SessionManager
) {
    fun login(phoneNumber: String, password: String): Single<SessionLoginResult> {
        return sessionApi.postSessions(SessionRequestBody(phoneNumber, password))
            .doOnSuccess { sessionManager.saveSession(it)  }
            .map<SessionLoginResult> { SessionLoginResult.Success(it) }
            .onErrorReturn {
                if (it.toApiError()?.code == InvalidPassword.code) {
                    InvalidPassword
                } else {
                    throw it
                }
            }
            .subscribeOn(Schedulers.io())
    }

    fun isLoggedIn(): Single<Boolean> = sessionManager.isLoggedIn()
}
