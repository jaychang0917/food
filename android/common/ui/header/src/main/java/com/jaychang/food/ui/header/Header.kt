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

package com.jaychang.food.ui.header

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class Header @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, androidx.appcompat.R.attr.toolbarStyle) {
    private val headerHeight = context.resources.getDimensionPixelSize(R.dimen.ui_header_height)

    var navigationType: NavigationType = NavigationType.BACK
        set(value) {
            field = value
            setNavigationIcon(value.value)
        }

    init {
        elevation = 0f
        setBackgroundResource(android.R.color.white)
        setupAttrs(attrs, defStyleAttr)
    }

    private fun setupAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.Header, defStyleAttr, 0)

        navigationType = NavigationType.from(typedArray.getInt(R.styleable.Header_navigationType, this.navigationType.value))

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, headerHeight)
    }

    fun setNavigationOnClickListener(listener: OnNavigationClickListener) {
        setNavigationOnClickListener {
            listener.onClick()
        }
    }

    interface OnNavigationClickListener {
        fun onClick()
    }

    enum class NavigationType(@DrawableRes val value: Int) {
        CLOSE(R.drawable.ic_close),
        BACK(R.drawable.ic_back);

        companion object {
            fun from(index: Int) = values().first { it.ordinal == index }
        }
    }
}
