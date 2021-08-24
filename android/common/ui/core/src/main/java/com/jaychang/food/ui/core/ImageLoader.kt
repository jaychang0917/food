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

package com.jaychang.food.ui.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.util.Base64
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.Coil
import coil.load
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.imageLoader
import coil.loadAny
import coil.request.ImageRequest
import java.io.File

typealias ImageRequestBuilder = ImageRequest.Builder

@JvmInline
value class Base64ImageData(val value: String)

fun ImageView.load(
    imageData: Base64ImageData?,
    builder: ImageRequestBuilder.() -> Unit = {}
) {
    val data = imageData?.value?.let { Base64.decode(it, Base64.DEFAULT) }
    loadAny(data, builder = builder)
}

fun ImageView.load(
    url: String?,
    builder: ImageRequestBuilder.() -> Unit = {}
) {
    val uri = if (url == null) null else Uri.parse(url)
    load(uri, builder = builder)
}

fun ImageView.load(
    uri: Uri?,
    builder: ImageRequestBuilder.() -> Unit = {}
) {
    load(uri, builder = builder)
}

fun ImageView.load(
    file: File?,
    builder: ImageRequestBuilder.() -> Unit = {}
) {
    load(file, builder = builder)
}

fun ImageView.load(
    @DrawableRes drawableResId: Int,
    builder: ImageRequestBuilder.() -> Unit = {}
) {
    load(drawableResId, builder = builder)
}

fun ImageView.load(
    bitmap: Bitmap?,
    builder: ImageRequestBuilder.() -> Unit = {}
) {
    load(bitmap, builder = builder)
}

fun ImageView.load(
    drawable: Drawable?,
    builder: ImageRequestBuilder.() -> Unit = {}
) {
    load(drawable, builder = builder)
}

object ImageLoader {
    fun init(context: Context) {
        val imageLoader = create(context)
        Coil.setImageLoader(imageLoader)
    }

    private fun create(context: Context): coil.ImageLoader {
        return coil.ImageLoader.Builder(context)
            .componentRegistry {
                // ByteArray
                add(ByteArrayFetcher())

                // GIFs
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder(context))
                } else {
                    add(GifDecoder())
                }

                // SVGs
                add(SvgDecoder(context))
            }
            .build()
    }

    fun fetch(
        context: Context,
        url: String,
        onSuccess: (Drawable) -> Unit = {},
        onError: () -> Unit = {}
    ) {
        val request = ImageRequest.Builder(context)
            .data(url)
            .target(onSuccess = onSuccess, onError = { onError() })
            .build()
        context.imageLoader.enqueue(request)
    }

    fun prefetch(context: Context, url: String) {
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()
        context.imageLoader.enqueue(request)
    }
}
