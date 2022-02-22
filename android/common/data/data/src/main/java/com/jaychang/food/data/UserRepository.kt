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

import com.jaychang.food.api.apis.UserApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

sealed class UserExistResult {
    data class Success(val data: Boolean) : UserExistResult()
    sealed class Error : UserExistResult() {
        object InvalidPhoneNumber : Error()
    }
}

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    fun exist(phoneNumber: String): Single<UserExistResult> {
        return userApi.getUsersUserId(phoneNumber)
            .toSingleDefault(true)
            .map<UserExistResult> {
                UserExistResult.Success(it)
            }
            .onErrorReturn {
                UserExistResult.Error.InvalidPhoneNumber
            }
            .subscribeOn(Schedulers.io())
    }
}
