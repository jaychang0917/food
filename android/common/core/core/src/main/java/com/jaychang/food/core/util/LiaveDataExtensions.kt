package com.jaychang.food.core.util

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.update(block: (T) -> T) {
    this.value = block(value!!)
}