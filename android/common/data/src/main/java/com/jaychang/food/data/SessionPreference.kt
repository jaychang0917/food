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
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

internal class SessionPreference(context: Context) {
    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyGenParameterSpec(AES256_GCM_SPEC)
        .build()
    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        FILE_NAME,
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getAccessToken(): Single<String> {
        return Single.fromCallable {
            sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        }
    }

    fun saveAccessToken(token: String): Completable {
        return Completable.fromAction {
            sharedPreferences.edit {
                putString(KEY_ACCESS_TOKEN, token)
            }
        }
    }

    companion object {
        const val FILE_NAME = "pref_session"
        private const val KEY_ACCESS_TOKEN = "access_token"
    }
}
