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

import coil.bitmap.BitmapPool
import coil.decode.DataSource
import coil.decode.Options
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.size.Size
import okio.buffer
import okio.source
import java.io.ByteArrayInputStream

internal class ByteArrayFetcher : Fetcher<ByteArray> {

    override fun key(data: ByteArray): String? = null

    override suspend fun fetch(
        pool: BitmapPool,
        data: ByteArray,
        size: Size,
        options: Options
    ): FetchResult {
        return SourceResult(
            source = ByteArrayInputStream(data).source().buffer(),
            mimeType = null,
            dataSource = DataSource.MEMORY
        )
    }
}