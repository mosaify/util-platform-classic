package dev.mosaify.util.platform

import io.klogging.NoCoLogging
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

internal expect fun getCurrentPlatformImpl(): Platform
internal expect fun getCurrentOsImpl(): OperatingSystem
internal expect fun getEnvImpl(name: String): String?
internal expect fun getHomeDirImpl(): String?

public object PlatformUtils : NoCoLogging {

    public fun getCurrentPlatform(): Platform = getCurrentPlatformImpl()

    public fun getCurrentOs(): OperatingSystem = getCurrentOsImpl()

    public fun getEnv(name: String): String? = getEnvImpl(name)

    public fun getHomeDir(): String? = getHomeDirImpl()

    public fun logWarning(message: () -> String): Unit = logger.warn { message() }

    public fun deleteRecursively(path: Path) {
        logWarning { "Deleting $path" }

        if (SystemFileSystem.metadataOrNull(path)?.isDirectory == true) {
            SystemFileSystem.list(path).forEach { deleteRecursively(it) }
        } else {
            SystemFileSystem.delete(path, mustExist = false)
        }
    }

    public fun walkRecursively(path: Path): Collection<Path> =
        if (SystemFileSystem.metadataOrNull(path)?.isDirectory == true)
            SystemFileSystem.list(path).flatMap { walkRecursively(it) }
        else listOf(path)
}
