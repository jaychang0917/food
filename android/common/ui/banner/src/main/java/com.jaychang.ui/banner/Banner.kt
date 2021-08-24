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

package com.jaychang.ui.banner

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar

class Banner private constructor(
    parent: ViewGroup,
    text: String,
    type: Type
) {
    private val contentView = BannerContentView(parent.context, text)
    private val snackbar = Snackbar.make(parent, text, LENGTH_INDEFINITE).apply {
        animationMode = ANIMATION_MODE_SLIDE
        val snackbarLayout = view as Snackbar.SnackbarLayout
        snackbarLayout.setBackgroundResource(type.color)
        snackbarLayout.setPadding(0, 0, 0, 0)
        snackbarLayout.post {
            (snackbarLayout.layoutParams as ViewGroup.MarginLayoutParams).setMargins(0)
            snackbarLayout.requestLayout()
        }
        snackbarLayout.elevation = 0f
        snackbarLayout.removeAllViews()
        snackbarLayout.addView(contentView)
    }

    fun show() {
        snackbar.show()
    }

    fun dismiss() {
        snackbar.dismiss()
    }

    companion object {
        fun neutral(parent: ViewGroup, text: String): Banner {
            return Banner(parent, text, Type.NEUTRAL)
        }

        fun error(parent: ViewGroup, text: String): Banner {
            return Banner(parent, text, Type.ERROR)
        }
    }

    private enum class Type(@ColorRes val color: Int) {
        NEUTRAL(R.color.primary),
        ERROR(R.color.error)
    }

    private class BannerContentView(context: Context, text: String) : AppCompatTextView(context) {
        init {
            val hor = context.resources.getDimensionPixelSize(R.dimen.spacing_small)
            val ver = context.resources.getDimensionPixelSize(R.dimen.spacing_small)
            setPadding(hor, ver, hor, ver)
            setTextAppearance(context, R.style.TextAppearance_Food_Body2)
            setTextColor(ContextCompat.getColor(context, android.R.color.white))
            this.text = text
        }
    }
}
