package dev.mosaify.util.platform

import io.klogging.config.ANSI_INFO
import io.klogging.config.loggingConfiguration
import io.klogging.noCoLogger

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual object PlatformLogging : PlatformLogger() {
    private val rootLogger = noCoLogger(About.NAME)

    init {
        loggingConfiguration { ANSI_INFO() }
    }

    override fun warn(message: () -> String) = rootLogger.warn { message() }
    override fun error(throwable: Throwable, message: () -> String) = rootLogger.error(throwable) { message() }
}
