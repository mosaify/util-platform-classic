package dev.mosaify.util.platform

import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

internal expect fun getCurrentOsImpl(): OperatingSystem
internal expect fun getEnvImpl(name: String): String?
internal expect fun getHomeDirImpl(): String?

public object PlatformUtils {

    public fun getCurrentOs(): OperatingSystem = getCurrentOsImpl()

    public fun getEnv(name: String): String? = getEnvImpl(name)

    public fun getHomeDir(): String? = getHomeDirImpl()

    public fun logWarning(message: () -> String): Unit = PlatformLogging.warn(message)

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
