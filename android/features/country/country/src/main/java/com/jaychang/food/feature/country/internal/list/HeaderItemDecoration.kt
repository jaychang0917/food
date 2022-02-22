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

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.view.View
import androidx.collection.SparseArrayCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaychang.food.feature.country.R
import com.jaychang.food.feature.country.internal.item.CountryItemUiState

internal class HeaderItemDecoration(
    private val context: Context
) : RecyclerView.ItemDecoration() {
    private val headerHeight = context.resources.getDimensionPixelSize(R.dimen.ui_header_height)
    private val headerTextSize = context.resources.getDimensionPixelSize(R.dimen.text_size_body1)
    // Records initial of first item text and its adapter position
    private val firstItemMap = SparseArrayCompat<String>()

    private var shouldShowHeader = true

    private val textPaint = TextPaint().apply {
        isAntiAlias = true
        color = ContextCompat.getColor(context, R.color.country_country_item_header)
        textSize = headerTextSize.toFloat()
    }
    private val headerBound = Rect()
    private val headerPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.background)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val item = toItem(parent, view) ?: return
        if (item.hasHeader) {
            outRect.set(0, headerHeight, 0, 0)
            firstItemMap.put(item.position, item.header?.toString())
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun toItem(parent: RecyclerView, view: View): Item? {
        val items = (parent.adapter as ListAdapter<CountryItemUiState, *>).currentList
        val itemPos = parent.getChildAdapterPosition(view)
        // Data invalidated, just return
        if (itemPos == RecyclerView.NO_POSITION) return null
        val preItemPos = (itemPos - 1).coerceAtLeast(0)
        val item = items[itemPos]
        val preItem = items[preItemPos]
        val hasSameInitial = preItem.name!![0].equals(item.name!![0], ignoreCase = true)
        val shouldDrawHeader = shouldShowHeader && (!hasSameInitial || itemPos == 0)
        return Item(shouldDrawHeader, item.name!![0], itemPos)
    }

    private data class Item(val hasHeader: Boolean, val header: Char?, val position: Int)

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (!shouldShowHeader) return

        for (child in parent.children) {
            canvas.drawHeader(parent, child)
        }
    }

    private fun Canvas.drawHeader(parent: RecyclerView, child: View) {
        val childPos = parent.getChildAdapterPosition(child)
        val text = firstItemMap.get(childPos) ?: return

        headerBound.set(parent.left, child.top - headerHeight, parent.right, child.top)
        drawRect(headerBound, headerPaint)

        val x = child.paddingStart.toFloat()
        val y = (child.top - headerTextSize).toFloat()
        drawText(text, x, y, textPaint)
    }

    fun setHeaderVisible(visible: Boolean) {
        shouldShowHeader = visible
    }
}
