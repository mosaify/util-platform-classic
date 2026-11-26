import dev.samoylenko.util.platform.PlatformUtils
import io.klogging.config.ANSI_INFO
import io.klogging.config.loggingConfiguration
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.FreeSpec

class PlatformUtilsFreeSpec : FreeSpec({
    beforeSpec { loggingConfiguration { ANSI_INFO() } }

    "logs a warning" { shouldNotThrowAny { PlatformUtils.logWarning { "util-platform-classic logs on ${PlatformUtils.getCurrentPlatform()}" } } }
})
