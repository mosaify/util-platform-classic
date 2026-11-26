package dev.mosaify.util.platform

private val process: dynamic = js("globalThis.process")

internal actual fun getCurrentOsImpl(): OperatingSystem =
    if (jsTypeOf(process) == "object") {
        if (process.platform == "win32") OperatingSystem.WINDOWS
        else OperatingSystem.POSIX
    } else OperatingSystem.BROWSER

internal actual fun getEnvImpl(name: String): String? =
    if (getCurrentOsImpl() != OperatingSystem.BROWSER) process.env[name] as String?
    else null

internal actual fun getHomeDirImpl(): String? =
    if (getCurrentOsImpl() == OperatingSystem.WINDOWS) getEnvImpl("USERPROFILE")
    else getEnvImpl("HOME")
