package dev.mosaify.util.platform

internal abstract class PlatformLogger {
    open fun warn(message: () -> String) = println(message())
    open fun error(throwable: Throwable, message: () -> String) = println("${message()}: $throwable")
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect object PlatformLogging : PlatformLogger
