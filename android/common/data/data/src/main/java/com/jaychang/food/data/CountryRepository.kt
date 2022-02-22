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

import android.app.Application
import android.os.Build
import com.jaychang.food.api.apis.ConfigApi
import com.jaychang.food.api.models.Country
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(
    private val configApi: ConfigApi,
    private val app: Application
) {
    private val currentRegion = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        app.resources.configuration.locales[0].country
    } else {
        app.resources.configuration.locale.country
    }
    private val _defaultCountry = BehaviorSubject.create<Country>()
    val defaultCountry: Observable<Country> = _defaultCountry

    fun all(): Single<List<Country>> {
        return configApi.getCountries()
            .map { country -> country.sortedBy { it.name } }
            .subscribeOn(Schedulers.io())
    }

    fun default(): Single<Country> {
        return all()
            .map { list ->
                val defaultRegion = _defaultCountry.value?.id ?: currentRegion
                list.first { it.id == defaultRegion }
            }
            .subscribeOn(Schedulers.io())
    }

    fun updateDefaultCountry(country: Country) {
        _defaultCountry.onNext(country)
    }
}
