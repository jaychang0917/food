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

package com.jaychang.food.feature.country.internal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import com.jaychang.food.feature.country.databinding.CountrySelectionBinding

internal class CountrySelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = CountrySelectionBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.sectionIndexView.onLetterSelected = {
            binding.countryListView.scrollToSection(it)
        }
    }

    fun setViewModel(lifecycleOwner: LifecycleOwner, viewModel: CountrySelectionViewModel) {
        binding.lifecycleOwner = lifecycleOwner
        binding.viewModel = viewModel
    }
}
