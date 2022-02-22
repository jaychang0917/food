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

package com.jaychang.food.feature.root.internal

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.jaychang.food.feature.root.R
import com.jaychang.food.feature.root.databinding.RootBinding
import dev.chrisbanes.insetter.applyInsetter

internal class RootView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = RootBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        setBackgroundResource(R.color.primary)

        binding.logoImageView.applyInsetter {
            type(statusBars = true) {
                margin()
            }
        }
    }

    fun doAfterLogoAnimation(finished: () -> Unit) {
        binding.logoImageView.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                binding.logoPlaceholder.isVisible = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                finished()
            }
        })
        binding.logoImageView.setMinProgress(15 / 100f)
        binding.logoImageView.playAnimation()
    }
}
