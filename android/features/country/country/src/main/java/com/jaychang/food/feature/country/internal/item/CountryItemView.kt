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

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import com.jaychang.food.feature.country.R
import com.jaychang.food.feature.country.databinding.CountryItemBinding

internal class CountryItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = CountryItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val height = resources.getDimensionPixelSize(R.dimen.country_item_height)
        layoutParams = LayoutParams(MATCH_PARENT, height)
        val horPadding = resources.getDimensionPixelSize(R.dimen.spacing_normal)
        setPadding(horPadding, 0, horPadding, 0)
        orientation = HORIZONTAL
    }

    fun setState(state: CountryItemUiState?) {
        state?.let {
            binding.state = it
        }
    }

    fun flush() {
        binding.executePendingBindings()
    }
}
