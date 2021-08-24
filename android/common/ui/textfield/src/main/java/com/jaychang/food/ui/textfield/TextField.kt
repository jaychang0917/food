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

package com.jaychang.food.ui.textfield

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.core.widget.doOnTextChanged
import com.jaychang.food.ui.core.Base64ImageData
import com.jaychang.food.ui.core.load
import com.jaychang.food.ui.textfield.databinding.UiTextfieldBinding

class TextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = UiTextfieldBinding.inflate(LayoutInflater.from(context), this)

    var isEditable = true
        set(value) {
            field = value
            binding.editText.isViewOnly = !value
        }

    var text: String = ""
        get() = binding.editText.text.toString()
        set(value) {
            field = value
            binding.editText.setText(value)
            binding.editText.setSelection(text.length)
        }

    var hint: String = ""
        set(value) {
            field = value
            binding.editText.hint = value
        }

    var prefix: String = ""
        set(value) {
            field = value
            binding.prefixTextView.text = value
        }

    var type: Type = Type.TEXT
        set(value) {
            field = value
            binding.editText.inputType = value.value
            binding.editText.setSelection(text.length)
        }

    var leadingIconData: String? = null
        set(value) {
            field = value
            val data = value?.let { Base64ImageData(it) }
            binding.leadingIconView.isVisible = value != null
            binding.leadingIconView.load(data)
        }

    @DrawableRes
    var leadingIconResource: Int? = null
        set(value) {
            field = value
            val icon = value?.let { ContextCompat.getDrawable(context, it) }
            binding.leadingIconView.isVisible = value != null
            binding.leadingIconView.setImageDrawable(icon)
        }

    var leadingIcon: Drawable? = null
        set(value) {
            field = value
            binding.leadingIconView.isVisible = value != null
            binding.leadingIconView.setImageDrawable(value)
        }

    val editText get() = binding.editText

    init {
        layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        minimumHeight = context.resources.getDimensionPixelSize(R.dimen.ui_textfield_min_height)
        orientation = HORIZONTAL
        setPadding(context.resources.getDimensionPixelSize(R.dimen.spacing_small))
        gravity = Gravity.CENTER_VERTICAL
        setBackgroundResource(R.drawable.ui_textfield_grey_bg)

        setupAttrs(attrs, defStyleAttr)
    }

    private fun setupAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.TextField, defStyleAttr, 0)

        isEditable = typedArray.getBoolean(R.styleable.TextField_editable, this.isEditable)

        hint = typedArray.getString(R.styleable.TextField_android_hint) ?: this.hint

        text = typedArray.getString(R.styleable.TextField_android_text) ?: this.text

        type = Type.from(typedArray.getInt(R.styleable.TextField_type, this.type.value))

        leadingIcon = typedArray.getDrawable(R.styleable.TextField_leadingIcon)

        prefix = typedArray.getString(R.styleable.TextField_prefix) ?: ""

        typedArray.recycle()
    }

    fun setOnLeadingIconClickListener(listener: OnLeadingIconClickListener) {
        binding.leadingIconView.setOnClickListener {
            listener.onClicked()
        }
    }

    interface OnLeadingIconClickListener {
        fun onClicked()
    }

    fun setOnTextChangedListener(listener: OnTextChangeListener) {
        binding.editText.doOnTextChanged { text, _, _, _ ->
            listener.onTextChanged(text.toString())
        }
    }

    interface OnTextChangeListener {
        fun onTextChanged(text: String)
    }

    enum class Type(val value: Int) {
        TEXT(InputType.TYPE_CLASS_TEXT),
        PASSWORD(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD),
        PHONE(InputType.TYPE_CLASS_NUMBER);

        companion object {
            fun from(index: Int) = values().first { it.ordinal == index }
        }
    }
}
