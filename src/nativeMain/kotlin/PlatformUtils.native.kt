@file:OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)

package dev.mosaify.util.platform

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.posix.getenv
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.OsFamily

internal actual fun getCurrentOsImpl(): OperatingSystem =
    if (Platform.osFamily == OsFamily.WINDOWS) OperatingSystem.WINDOWS
    else OperatingSystem.POSIX

internal actual fun getEnvImpl(name: String): String? =
    getenv(name)?.toKString()

internal actual fun getHomeDirImpl(): String? =
    if (getCurrentOsImpl() == OperatingSystem.WINDOWS) getEnvImpl("USERPROFILE")
    else getEnvImpl("HOME")
