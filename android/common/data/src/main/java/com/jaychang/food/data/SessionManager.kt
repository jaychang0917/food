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

import android.content.Context
import com.jaychang.food.api.models.Session
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {
    private val sessionPreference = SessionPreference(context)
    private var _session: Session? = null
    val session: Session? get() = _session

    fun saveSession(session: Session) {
        this._session = session
        sessionPreference.saveAccessToken(session.accessToken).subscribe()
    }

    fun clearSession() {
        _session = null
        sessionPreference.saveAccessToken("").subscribe()
    }

    fun isLoggedIn(): Single<Boolean> {
        if (_session != null) return Single.just(true)
        return sessionPreference.getAccessToken().map { it.isNotEmpty() }
    }
}
