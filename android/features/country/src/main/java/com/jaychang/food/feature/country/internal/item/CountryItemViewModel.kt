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

package com.jaychang.food.feature.country.internal.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaychang.food.api.models.Country
import com.jaychang.food.core.arch.Event

class CountryItemViewModel(
    val country: Country,
    val selected: Country,
    var onClicked: MutableLiveData<Event<CountryItemViewModel>>
) {

    val code: LiveData<String> = MutableLiveData(country.code)

    val name: LiveData<String> = MutableLiveData(country.name)

    val flag: LiveData<String> = MutableLiveData(country.imageData)

    val tickIconVisible: LiveData<Boolean> = MutableLiveData(country == selected)

    fun onClicked() {
        onClicked.value = Event(this)
    }
}
