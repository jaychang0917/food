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
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaychang.food.feature.country.databinding.CountryItemBinding
import com.jaychang.food.feature.country.internal.item.CountryItemViewModel

class CountryListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private val layoutInflater = LayoutInflater.from(context)
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

    private inner class ItemAdapter : ListAdapter<CountryItemViewModel, ItemViewHolder>(ItemCallback) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val binding = CountryItemBinding.inflate(layoutInflater, parent, false)
            return ItemViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val item = getItem(position)
            holder.binding.viewModel = item
            // Don't wait until next frame
            holder.binding.executePendingBindings()
        }
    }

    private object ItemCallback : DiffUtil.ItemCallback<CountryItemViewModel>() {
        override fun areItemsTheSame(oldItem: CountryItemViewModel, newItem: CountryItemViewModel) = oldItem == newItem

        override fun areContentsTheSame(oldItem: CountryItemViewModel, newItem: CountryItemViewModel) = oldItem.code.value == newItem.code.value
    }

    private class ItemViewHolder(val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun setItems(items: List<CountryItemViewModel>) {
        itemAdapter.submitList(items)
    }

    fun setHeaderVisible(visible: Boolean) {
        headerItemDecoration.setHeaderVisible(visible)
        invalidateItemDecorations()
    }

    fun scrollToSection(header: String) {
        val items = itemAdapter.currentList
        val position = items.indexOfFirst { it.name.value!!.startsWith(header) }
        linearLayoutManager.scrollToPositionWithOffset(position, 0)
    }
}
