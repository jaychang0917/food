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

package com.jaychang.food.feature.country.internal.list

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar

internal class SectionIndexView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs, android.R.attr.textViewStyle) {
    private var currentLetterPosition = 0
    private var preLetterPosition = -1
    private lateinit var keyboardVisibilityUnregistrar: Unregistrar

    var onLetterSelected: ((String) -> Unit)? = null

    init {
        minLines = LETTERS.length
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        gravity = Gravity.CENTER_VERTICAL
        text = LETTERS.map { "$it" }.joinToString("\n")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val activity = (context as ContextWrapper).baseContext as Activity
        keyboardVisibilityUnregistrar = KeyboardVisibilityEvent.registerEventListener(activity) {
            isVisible = !it
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        keyboardVisibilityUnregistrar.unregister()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> return handleDown(event)
            MotionEvent.ACTION_MOVE -> return handleMove(event)
            MotionEvent.ACTION_UP -> return handleUp()
        }
        return false
    }

    private fun handleDown(event: MotionEvent): Boolean {
        updatePosition(event)
        return true
    }

    private fun handleMove(event: MotionEvent): Boolean {
        updatePosition(event)
        dispatchPositionUpdated()
        return true
    }

    private fun handleUp(): Boolean {
        performClick()
        return true
    }

    // Handles accessibility
    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun updatePosition(event: MotionEvent) {
        val curPos = (event.y / height * LETTERS.length).toInt()
        currentLetterPosition = curPos.coerceIn(0, LETTERS.length - 1)
    }

    private fun dispatchPositionUpdated() {
        if (currentLetterPosition != preLetterPosition) {
            onLetterSelected?.invoke(LETTERS[currentLetterPosition].toString())
            preLetterPosition = currentLetterPosition
        }
    }

    companion object {
        private const val LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    }
}
