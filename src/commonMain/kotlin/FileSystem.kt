package dev.mosaify.util.platform

import io.klogging.NoCoLogging
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.io.readString

public object FileSystem : NoCoLogging {
    public fun readText(path: Path): String = SystemFileSystem.source(path).buffered().use { it.readString() }
    public fun readTextOrNull(path: Path): String? = runCatching { readText(path) }
        .onFailure { logger.error(throwable = it) { "Could not read file '$path'" } }
        .getOrNull()

    public fun readBytes(path: Path): ByteArray = SystemFileSystem.source(path).buffered().use { it.readByteArray() }
    public fun readBytesOrNull(path: Path): ByteArray? = runCatching { readBytes(path) }
        .onFailure { logger.error(throwable = it) { "Could not read file '$path'" } }
        .getOrNull()
}
