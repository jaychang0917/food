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

package com.jaychang.food.feature.landing.internal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import com.jaychang.food.api.models.Country
import com.jaychang.food.component.landing.databinding.LandingBinding
import com.jaychang.food.ui.textfield.TextField

@BindingAdapter("country")
internal fun TextField.setCountry(country: Country?) {
    if (country == null) return
    prefix = country.code
    leadingIconData = country.imageData
}

internal class LandingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = LandingBinding.inflate(LayoutInflater.from(context), this, true)

    fun setViewModel(lifecycleOwner: LifecycleOwner, viewModel: LandingViewModel) {
        binding.lifecycleOwner = lifecycleOwner
        binding.viewModel = viewModel
    }
}
