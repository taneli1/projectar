package com.example.projectar.data.utils

import com.example.projectar.data.room.entity.file.FileInfo


/**
 * Convenience method to handle caching with FileInfo.
 */
fun <V> LinkedHashMap<Long, V>.cacheFileInfo(
    info: FileInfo,
    data: V,
    maxCacheSize: Int
) {
    if (this.size == maxCacheSize) {
        // Remove the item that was added first
        this.remove(this.iterator().next().key)
        this[info.id] = data
    } else {
        this[info.id] = data
    }
}
