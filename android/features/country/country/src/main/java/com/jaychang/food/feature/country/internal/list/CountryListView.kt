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
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaychang.food.feature.country.internal.item.CountryItemUiState
import com.jaychang.food.feature.country.internal.item.CountryItemView

internal class CountryListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private val itemAdapter = ItemAdapter()
    private val linearLayoutManager = LinearLayoutManager(context)
    private val headerItemDecoration = HeaderItemDecoration(context)

    init {
        layoutManager = linearLayoutManager
        setHasFixedSize(true)
        itemAnimator = null
        addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
        addItemDecoration(headerItemDecoration)
        itemAdapter.registerAdapterDataObserver(AdapterDataObserver())
        adapter = itemAdapter
    }

    // RecyclerView will try to keep the previous items in frame after data updated
    // since it assumes that is what the user is looking at. In our case, we want to
    // scroll to the top of the list when data updated.
    private inner class AdapterDataObserver : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (positionStart == 0) {
                linearLayoutManager.scrollToPositionWithOffset(0, 0)
            }
        }
    }

    private inner class ItemAdapter : ListAdapter<CountryItemUiState, ItemViewHolder>(ItemCallback) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val itemView = CountryItemView(context)
            return ItemViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val itemState = getItem(position)
            holder.view.setState(itemState)
            holder.view.setOnClickListener { itemState.onItemClicked() }
            // Flush pending bindings while scroll to position
            holder.view.flush()
        }
    }

    private object ItemCallback : DiffUtil.ItemCallback<CountryItemUiState>() {
        override fun areItemsTheSame(oldItem: CountryItemUiState, newItem: CountryItemUiState) = oldItem == newItem

        override fun areContentsTheSame(oldItem: CountryItemUiState, newItem: CountryItemUiState) = oldItem.name == newItem.name
    }

    private class ItemViewHolder(val view: CountryItemView) : RecyclerView.ViewHolder(view)

    fun setStates(states: List<CountryItemUiState>?) {
        states?.let {
            itemAdapter.submitList(it)
        }
    }

    fun setHeaderVisible(visible: Boolean) {
        headerItemDecoration.setHeaderVisible(visible)
        invalidateItemDecorations()
    }

    fun scrollToSection(header: String) {
        val items = itemAdapter.currentList
        val position = items.indexOfFirst { it.name?.startsWith(header) == true }
        linearLayoutManager.scrollToPositionWithOffset(position, 0)
    }
}
